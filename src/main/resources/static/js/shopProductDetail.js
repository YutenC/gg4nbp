import { host_context, nowDate } from './shopproductCommon.js';
import { saveDataToSessionStorage, getDataFromSessionStorage } from './shopproductCommon.js';

// { id: 3, text: "注意事項", action: "3", flag: false }
const vm = Vue.createApp({
    data() {
        return {
            amountArray: [1, 2, 3, 4, 5],
            initSelectedAmountIndex: 0,
            ProductDetail_id: 0,
            currentMainguideContent: 1,
            mainguideContent: [{ id: 1, text: "商品特色", action: "1", flag: true },
            { id: 2, text: "商品評論", action: "2", flag: false }
            ],
            nowDate: '',
            minDate: '',
            product: {},
            productDetail: {},
            productHistory: [],
            buyAmount: 1,
            comments: '',
            smallImageIndex: 0,
            isHistoryAreaHidden: true,
            productScore: ""
        };
    },
    created() {
        console.log('created');
    },
    mounted() {
        getProductDetail();
        getProductHistory();
    },
    methods: {
        getProduct: function () {
            const ProductDetail_id = getDataFromSessionStorage("currentShopProductDetail_id");
            axios({
                method: "GET",
                url: host_context + "shopDispatcher/getProductDetail",
                // withCredentials: true,
                params: { id: ProductDetail_id }
            })
                .then(function (value) {
                    vm.productDetail = value.data;
                    console.log("getProductById then");

                })
                .catch(function (e) {
                    console.log("getProductById error " + e);
                });
        },

        addCart: function (action, id) {
            let transObj = { productId: id, buyAmount: vm.buyAmount };
            axios({
                method: "Post",
                url: host_context + "ShoppingList",
                // withCredentials: true,
                // crossDomain: true,
                params: {
                    demand: "addOneShoppingList",
                    transObj: JSON.stringify(transObj),
                }
            })
                .then(function (response) {


                    let result = response.data;
                    if (result != null && result.redirect) {
                        window.location.href = "/gg4nbp/member_login.html";
                    }
                    else {
                        if (action == 1) {
                            window.location.href = "../member/shoppingCart(Vue).html";
                        }
                    }


                    // let result = response.value;
                    // if (!result.successful) {
                    //     window.location.href = "./member_login.html";
                    // }
                    // else {

                    // }

                    // // <button @click="addCart(1,product.id)">直接購買</button>
                    // // <button @click="addCart(0,product.id)">加入購物車</button>

                    // if (action == 1) {
                    //     window.location.href = "../member/shoppingCart(Vue).html";
                    // }
                    // else {
                    //     // if (response.data.state === "redirect") {
                    //     //     console.log(response.data.msg);
                    //     //     window.location.href = response.data.msg;
                    //     // }
                    // }

                    // console.log("addCart then");

                })
                .catch(function (e) {
                    console.log("addCart error " + e);
                });
        },
        addFollow: function (id) {
            axios({
                method: "Get",
                url: host_context + "shopDispatcher/addFollow",
                // withCredentials: true,
                // crossDomain: true,
                params: {
                    id: id,
                    redirectUrl: "http://localhost:8080/gg4nbp/shop/shopProductDetail.html"
                }
            })
                .then(function (value) {
                    let result = value.data;

                    if (result.state === "redirect") {
                        console.log(result.msg);
                        window.location.href = result.msg;
                    }
                    else if (result.state.toLowerCase() === "ok") {
                        vm.product.follow = result.content;
                    }


                    console.log("addFollow then");

                })
                .catch(function (e) {
                    console.log("addFollow error " + e);
                });


        },

        changeMainContent(state, event) {
            event.preventDefault();
            vm.mainguideContent[vm.currentMainguideContent - 1].flag = false;
            vm.mainguideContent[state - 1].flag = true;
            vm.currentMainguideContent = state;
            if (state == 2) {
                vm.getProductCommen();
            }

        },
        getProductCommen() {
            axios({
                method: "Get",
                url: host_context + "publicComment",
                // withCredentials: true,
                // crossDomain: true,
                params: {
                    getProductComments: vm.ProductDetail_id
                }
            })
                .then(function (value) {
                    let result = value.data;
                    // if (result.state.toLowerCase() === "ok") {
                    //     vm.product.follow = result.content;
                    // }
                    vm.comments = result;

                    console.log("getProductCommen then");

                })
                .catch(function (e) {
                    console.log("getProductCommen error " + e);
                });
        },
        changeAmount(buyAmount) {
            vm.buyAmount = parseInt(buyAmount);
        },
        clickPrevNext(e) {
            if (e.classList.contains('prev')) {
                vm.smallImageIndex = (--vm.smallImageIndex < 0) ? 0 : vm.smallImageIndex;
            } else if (e.classList.contains('next')) {
                vm.smallImageIndex = (++vm.smallImageIndex > 2) ? 2 : vm.smallImageIndex;
            }
        },
        // historymouseenter: function () {
        //     vm.isHistoryAreaHidden = false;
        // },
        // historymouseleave() {
        //     vm.isHistoryAreaHidden = true;
        // }

    },
}).mount("main");

const topNav = Vue.createApp({
    data() {
        return {
            login: false,
        }
    },
    mounted() {
        checkLogin();
    },
    methods: {
        getProductByType(type) {

        },
        loginBtn(event) {

            if (topNav.login) {
                axios({
                    method: "POST",
                    url: host_context + "member/memberLogoutServlet",
                    // withCredentials: true,
                    // crossDomain: true,
                },)
                    .then(function (value) {
                        console.log("memberLogoutServlet then");
                        getProductDetail();
                        topNav.login = false;
                    })
                    .catch(function (e) {
                        console.log("memberLogoutServlet error " + e);
                    });
            }
            else {
                window.location.href = "/gg4nbp/member_login.html";
            }

        }
    }
}).mount('#vue-member');

vm.initSelectedAmountIndex = 0;
// getProductDetail();


function getProductDetail() {
    const ProductDetail_id = getDataFromSessionStorage("currentShopProductDetail_id");

    axios({
        method: "GET",
        url: host_context + "shopDispatcher/getProductDetail",
        // withCredentials: true,
        params: { id: ProductDetail_id }
    })
        .then(function (value) {

            vm.productDetail = value.data;
            vm.product = vm.productDetail.product;
            vm.ProductDetail_id = ProductDetail_id;

            if (vm.product.revieweCount != 0) {
                let score = vm.product.rate / (vm.product.revieweCount * 5.0);
                score = score * 5;
                vm.productScore = score + "/5";
            }
            else {
                vm.productScore = "尚無評價分數"
            }

            getProductHistory();
            console.log("getProductById then");

        })
        .catch(function (e) {
            console.log("getProductById error " + e);
        });
}

function getProductHistory() {
    axios({
        method: "GET",
        url: host_context + "shopDispatcher/getProductHistory",
        // withCredentials: true,
    })
        .then(function (value) {
            vm.productHistory = value.data;
            console.log("getProductHistory then");

        })
        .catch(function (e) {
            console.log("getProductHistory error " + e);
        });
}



function checkLogin() {
    axios({
        method: "GET",
        url: host_context + "shopDispatcher/checkLogin",

    })
        .then(function (value) {
            let state = value.data.state;
            if (state === "ok") {
                let msg = value.data.msg;
                if (msg === "login") {
                    topNav.login = true;
                }
                else if (msg === "nologin") {
                    topNav.login = false;
                }

            }

        })
        .catch(function (e) {
            console.log("checkLogin error " + e);
        });
}