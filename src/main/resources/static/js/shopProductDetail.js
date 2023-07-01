import { host_context, nowDate } from './shopproductCommon.js';
import { saveDataToSessionStorage, getDataFromSessionStorage } from './shopproductCommon.js';



const vm = Vue.createApp({
    data() {
        return {

            ProductDetail_id: 0,
            currentMainguideContent: 1,
            mainguideContent: [{ id: 1, text: "商品特色", action: "1", flag: true },
            { id: 2, text: "商品評論", action: "2", flag: false },
            { id: 3, text: "注意事項", action: "3", flag: false }],
            nowDate: '',
            minDate: '',
            product: {},
            productDetail: {},
            productHistory: [],
            buyAmount: 1,
            comments: '',

        };
    },
    created() {




        console.log('created');
    },
    mounted() {
        // const ProductDetail_id = getDataFromSessionStorage("currentShopProductDetail_id");
        // axios({
        //     method: "GET",
        //     url: host_context + "shopDispatcher/getProductDetail",
        //     params: { id: ProductDetail_id }
        // })
        //     .then(function (value) {
        //         vm.productDetail = value.data;
        //         vm.product = vm.productDetail.product;
        //         console.log("getProductById then");

        //     })
        //     .catch(function (e) {
        //         console.log("getProductById error " + e);
        //     });

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
            // productId
            //buyAmount


            let transObj = { productId: id, buyAmount: vm.buyAmount };

            axios({
                method: "Post",
                url: host_context + "ShoppingList",
                // withCredentials: true,
                // crossDomain: true,
                params: {
                    demand: "addOneShoppingList",
                    transObj: JSON.stringify(transObj)
                }
            })
                .then(function (value) {

                    if (action == 1) {
                        window.location.href = "./shoppingCart(Vue).html";
                    }

                    console.log("addCart then");

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
                    id: id
                }
            })
                .then(function (value) {
                    let result = value.data;
                    if (result.state.toLowerCase() === "ok") {
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


            // event.target.classList.add("-on");
            // last_currentMainguideContent
        },
        getProductCommen() {
            axios({
                method: "Get",
                url: host_context + "OrderDetail",
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
            vm.buyAmount = buyAmount;
        }

        // historymouseenter: function () {
        //     // e.preventDefault();
        //     // let historyArea = this.$refs.historyArea;

        //     // if (!historyArea.classList.contains("hidden")) {
        //     //     historyArea.classList.remove("hidden");
        //     // }
        // }
        // 
    },
}).mount(".shopmain");

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