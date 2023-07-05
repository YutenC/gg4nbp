const memberOrder = Vue.createApp({
    data() {
        return {
            orders: [],
            showOrderStatus: ['', '已成立', '已取消', '申請取消', '申請退貨'],
            showDeliverState: ['未出貨', '已出貨', '已到貨'],
            showPayStatus: ['', '待付款', '已付款', '貨到付款'],
            showCommitType: ['', '信用卡', '轉帳', '貨到付款'],
            showPickType: ['', '宅配', '超商店取'],
            filterSelect: '1',
            offset: 0,
            listLength: 0,
            month: {
                Jan: 1,
                Feb: 2,
                Mar: 3,
                Apr: 4,
                May: 5,
                Jun: 6,
                Jul: 7,
                Aug: 8,
                Sep: 9,
                Oct: 10,
                Nov: 11,
                Dec: 12
            }
        }
    },
    methods: {
        cancleOrder(index, demand) {
            let asignOrder = this.orders[index].orderMaster;
            let msg = (demand === 'cancel') ? '確定取消?' : '確定放棄?';
            let status = (demand === 'cancel') ? 3 : 1;
            Swal.fire({
                title: msg,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: '是',
                cancelButtonText: '否'
            }).then(function (result) {
                if (result.isConfirmed) {
                    asignOrder.orderStatus = status;
                    axios({
                        method: 'post',
                        url: projectFolder + '/OrderMaster',
                        params: {
                            demand: 'updateOMFromMember',
                        },
                        data: {
                            order: asignOrder,
                        }
                    }).then(res => console.log(res))
                        .catch(err => console.log(err));
                } else {
                }
            });
            this.orders[index].orderMaster.orderStatus = asignOrder.orderStatus;
        },
        checkPay(index) {
            let msg = this.showCommitType[this.orders[index].orderMaster.commitType] + '\n';
            switch (this.orders[index].orderMaster.commitType) {
                case 2:
                    msg += '商城轉帳資訊: \n商城帳戶名：\nNBP.gg\n商城帳戶分行名：\nNBP.gg Branch\n' +
                        '商城帳戶：\n123 4567 8910 1112';
                    break;
                case 3:
                    msg += '請於取貨時付款';
                    break;
            }
            Swal.fire(msg);
        },
        dateFormat: function (dateStr) {
            let elmArr = dateStr.split(',');
            let monthKey = elmArr[0].split(' ')[0];
            let dayKey = elmArr[0].split(' ')[1];
            let formated = elmArr[1] + ' ' + this.month[monthKey] + '月 ' + dayKey + '日 ' + elmArr[2]
            return formated;
        },
        renewListWithSelect: function () {
            this.offset = 0;
            axios.get(projectFolder + '/OrderMaster?memberAll=0&criteria=' + this.filterSelect)
                .then(res => this.orders = res.data)
                .catch(err => console.log(err))
        },
        leave: function (location, otherDetail) {
            sessionStorage.setItem('productId', otherDetail);
            window.location.replace(projectHref + '/' + location);
        },
        sendComment: function (orderId, productId, event) {
            let star = $(event.target).closest('.rankDetail').find('input:checked').val();
            let comment = $(event.target).closest('.rankDetail').find('textarea.commentContent').val();
            let commentDetail = {
                orderId: orderId,
                productId: productId,
                star: star,
                comment: comment
            };
            axios({
                method: 'post',
                url: projectFolder + '/OrderDetail',
                params: {
                    comment: 'y',
                },
                data: {
                    commentDetail: commentDetail
                }
            }).then(res => console.log(res))
                .catch(err => console.log(err));

            $(event.target).closest('.rankDetail').find('input').prop('disabled', true);
            $(event.target).closest('.rankDetail').find('textarea.commentContent').prop('disabled', true);
            $(event.target).prop('disabled', true);

            for (let i = 0; i < this.orders.length; i++) {
                if (this.orders[i].orderMaster.orderId === orderId) {
                    this.orders[i].trList.comment = comment;
                }
            }
        },
    },
    created() {
        axios.get(projectFolder + '/OrderMaster?memberAll=0')
            .then(res => this.orders = res.data)
            .catch(err => console.log(err));

        axios.get(projectFolder + '/OrderMaster?countListLength=member&matchId=y')
            .then(res => this.listLength = res.data)
            .catch(err => console.log(err));
    }
}).mount('.memberOrder');

function scrollList() {
    memberOrder.$data.offset += 10
    axios.get(projectFolder + '/OrderMaster?memberAll=' + memberOrder.$data.offset + '&criteria=' + memberOrder.$data.filterSelect)
        .then(res => {
            for (let elm of res.data) {
                memberOrder.$data.orders.push(elm);
            }
        })
        .catch(err => console.log(err))
}

window.addEventListener('scroll', function () {
    //獲取滾動條滾動距離（為了消除標準模式和怪異模式之間的差別而做的相容）
    let scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
    //獲取最後一個box到頂部的距離+ 它自身一半的距離
    let elms = document.querySelectorAll('.detailUnit');
    let lastElmTop = 0;
    let lastElmHeight = 0;
    for (let ele of elms) {
        lastElmTop = ele.offsetTop;
        lastElmHeight = ele.offsetHeight;
    }

    //非關聯元素高度依響應式網站而有變化
    let adjust = document.querySelector('.col-lg-4').offsetHeight;

    if (scrollTop > adjust && (scrollTop + adjust >= lastElmTop + lastElmHeight)) {
        scrollList();
    }
});

// 開關檢視訂單詳情
$('div.detailArea').on('click', 'a.checkDetail', function (e) {
    e.preventDefault();
    $(this).closest('div.detailUnit').find('div.detailContent').toggleClass('on');
});

// 開關檢視商品評價
$('div.detailArea').on('click', 'a.comment', function (e) {
    e.preventDefault();
    $(this).closest('div.shoplist').find('div.commentContent').toggleClass('on');
});
