// 路徑常數
const projectFolder = '/gg4nbp';

const orderContent = Vue.createApp({
    data() {
        return {
            order: {},
            orderDetails: [],
            updateOrder: { orderStatus: '1', deliverNumber: '', deliverState: '0', payStatus: '1' },
            showPickType: ['', '宅配', '超商店取'],
            showCommitType: ['', '信用卡', '轉帳', '貨到付款'],
            showOrderStatus: ['', '已成立', '已取消', '申請取消', '申請退貨'],
            showDeliverState: ['未出貨', '已出貨', '已到貨'],
            showPayStatus: ['', '待付款', '已付款', '貨到付款'],
        }
    },
    methods: {
        goBack() {
            history.back();
        },
        updateOrderStatus: function (param) {
            this.updateOrder.orderStatus = param;
        },
        confirm: function () {
            let order = this.order;
            let cancel = this.cancel;
            let updateOrder = this.updateOrder;
            Swal.fire({
                title: '確定送出?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: '是',
                cancelButtonText: '否'
            }).then(function (result) {
                if (result.isConfirmed) {
                    for (let key in updateOrder) {
                        order[`${key}`] = updateOrder[`${key}`];
                    }
                    axios({
                        method: 'post',
                        url: projectFolder + '/OrderMaster',
                        params: {
                            demand: 'updateOMfromManager',
                        },
                        data: {
                            order: order,
                        }
                    }).then(res => console.log(res))
                        .catch(err => console.log(err));
                } else {
                    cancel();
                }
            });
        },
        leave: function (location, otherDetail, event) {
            event.preventDefault();
            sessionStorage.setItem('productId', otherDetail);
            window.location.href = projectHref + '/' + location;
        },
        cancel: function () {
            for (let key in this.updateOrder) {
                this.updateOrder[`${key}`] = this.order[`${key}`];
            }
        }
    },
    created() {
        let orderId = sessionStorage.getItem('orderId');
        axios.get(projectFolder + '/OrderMaster?getOne=' + orderId)
            .then(res => {
                this.order = res.data;
                this.updateOrder.orderStatus = this.order.orderStatus;
                this.updateOrder.deliverNumber = this.order.deliverNumber;
                this.updateOrder.deliverState = this.order.deliverState;
                this.updateOrder.payStatus = this.order.payStatus;
            })
            .catch(err => console.log(err));

        axios.get(projectFolder + '/OrderDetail?getByOrderId=' + orderId)
            .then(res => this.orderDetails = res.data)
            .catch(err => console.log(err));
    }
}).mount('#orderContent');