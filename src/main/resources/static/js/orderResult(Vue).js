(() => {
    // 購物明細
    const shoppingContent = Vue.createApp({
        data() {
            return {
                // 購物商品明細
                odProducts: [],
                // 消費折抵
                checkCoupon: '',
                nowBonus: '',
                usedBonus: '',
                // 配送方式
                deliver: '',
                address: {},
                // 付款方式
                payment: '',
                getBonus: 0,
            }
        },
        methods: {
            leave: function (location, otherDetail) {
                sessionStorage.setItem('productId', otherDetail);
                Swal.fire({
                    title: '確定離開?',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: '是',
                    cancelButtonText: '否'
                }).then(function (result) {
                    if (result.isConfirmed) {
                        // 清空
                        clearSessionStg();
                        window.location.replace(projectFolder + '/' + location);
                    }
                });
            }
        },
        computed: {
            productSubtotal: function () {
                let productSub = 0;
                let discount = this.checkCoupon === null ? 0 : this.checkCoupon.discount;
                for (pic of this.odProducts) {
                    productSub += pic.buyAmount * pic.price;
                }

                let finalPrice = productSub - discount - this.usedBonus;

                return finalPrice;
            },
            deliverCal() {
                return this.deliver === 'toCvs' ? 200 : 100;
            },
        },
        created() {
            // 取得
            this.odProducts = JSON.parse(sessionStorage.getItem('odProducts'));
            this.checkCoupon = JSON.parse(sessionStorage.getItem('checkCoupon'));
            this.usedBonus = Number.parseInt(sessionStorage.getItem('usedBonus'));
            this.nowBonus = Number.parseFloat(sessionStorage.getItem('nowBonus'));
            this.payment = sessionStorage.getItem('payment');
            this.deliver = sessionStorage.getItem('deliver');
            this.address = JSON.parse(sessionStorage.getItem('address'));
            this.getBonus = sessionStorage.getItem("getBonus");
        }
    }).mount('.shoppingContent');

    // 推薦商品呈現
    const promoProduct = Vue.createApp({
        data() {
            return {
                promoProduct: [],
                recomendAmount: 5
            }
        },
        methods: {
            leave: function (location, otherDetail) {
                sessionStorage.setItem('productId', otherDetail);
                Swal.fire({
                    title: '確定離開?',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: '是',
                    cancelButtonText: '否'
                }).then(function (result) {
                    if (result.isConfirmed) {
                        // 清空
                        clearSessionStg();
                        window.location.replace(projectFolder + '/' + location);
                    }
                });
            }
        },
        created() {
            const sort = { action: 'order', key: 'desc', value: 'buyTimes' };
            const require = ['productIndexImage'];
            const sqlConditions = [{ key: 'limit', value: this.recomendAmount }];
            const conditions = [{ key: "state", value: 1 }];
            const req = { sort: sort, required: require, sqlConditions: sqlConditions, conditions: conditions };
            axios({
                method: 'get',
                url: projectFolder + '/shopDispatcher/getAllProductByCondition',
                params: {
                    params: encodeURIComponent(JSON.stringify(req))
                }
            }).then(res => this.promoProduct = res.data)
                .catch(err => console.log(err));
        }
    }).mount('#promoProduct');

    // 按下上一頁後無法返回 -- > Chrome瀏覽器限制無法順利觸發事件，待解決
    window.addEventListener('popstate', () => {
        Swal.fire({
            title: '確定離開?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '是',
            cancelButtonText: '否'
        }).then(function (result) {
            if (result.isConfirmed) {

            }
        });
    });

    $('.logo').on('click', (e) => {
        e.preventDefault();
        Swal.fire({
            title: '確定離開?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '是',
            cancelButtonText: '否'
        }).then(function (result) {
            if (result.isConfirmed) {
                clearSessionStg();
                location.href = projectFolder + '/shop/shopIndex(Vue).html';
            }
        });
    });

    function clearSessionStg() {
        // 清空
        sessionStorage.removeItem('odProducts');
        sessionStorage.removeItem('checkCoupon');
        sessionStorage.removeItem('usedBonus');
        sessionStorage.removeItem('nowBonus');
        sessionStorage.removeItem('payment');
        sessionStorage.removeItem('deliver');
        sessionStorage.removeItem('address');
        sessionStorage.removeItem("getBonus");
    };
})();