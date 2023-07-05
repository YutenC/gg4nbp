/* ------ minimenu控制區 ------- */

// 網頁下滑超過150px顯示
$(window).on('scroll', () => {
    if (scrollY > 500 && scrollY < 1375) {
        $('main .miniMenu').css('display', 'block');
    } else {
        $('main .miniMenu').css('display', 'none');
    }

});

// 右方迷你功能列
const miniMenuContent = Vue.createApp({
    data() {
        return {
            productHistory: [],
            isHistoryAreaHidden: true,
        }
    },
    methods: {
        historymouseenter: function () {
            this.isHistoryAreaHidden = false;
        },
        historymouseleave() {
            this.isHistoryAreaHidden = true;
        },
        goTop(event) {
            event.preventDefault();
            document.body.scrollTop = 0;
            document.documentElement.scrollTop = 0;
        },
        goProduct: function (location, otherDetail) {
            sessionStorage.setItem('currentShopProductDetail_id', otherDetail);
            window.location.href = projectFolder + '/' + location;
        },
        goCart(event) {
            event.preventDefault();
            axios.post(projectFolder + '/ShoppingList')
                .then(res => {
                    if (!res.data.successful) {
                        let timerInterval
                        Swal.fire({
                            title: '您尚未登入！',
                            html: ' <b></b> 秒後跳轉到登入頁面',
                            timer: 3000,
                            timerProgressBar: false,
                            didOpen: () => {
                                Swal.showLoading()
                                const b = Swal.getHtmlContainer().querySelector('b')
                                timerInterval = setInterval(() => {
                                    b.textContent = Math.floor(Swal.getTimerLeft() / 1000)
                                }, 100)
                            },
                            willClose: () => {
                                clearInterval(timerInterval)
                            }
                        }).then((result) => {
                            if (result.dismiss === Swal.DismissReason.timer) {
                                sessionStorage.setItem('backTo', window.location.href);
                                window.location.href = projectFolder + '/member_login.html';
                            }
                        });
                    } else {
                        window.location.href = projectFolder + '/member/shoppingCart(Vue).html';
                    }
                })
                .catch(err => console.log(err));
        }
    },
    created() {
        axios({
            method: "GET",
            url: projectFolder + "/shopDispatcher/getProductHistory",
        })
            .then(res => {
                this.productHistory = res.data;
                console.log("getProductHistory then");

            })
            .catch(function (e) {
                console.log("getProductHistory error " + e);
            });
    }
}).mount('.miniMenuContent');


