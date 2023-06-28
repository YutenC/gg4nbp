import { host_context, nowDate } from './shopproductCommon.js';
import { saveDataToSessionStorage, getDataFromSessionStorage } from './shopproductCommon.js';



const vm = Vue.createApp({
    data() {
        return {
            nowDate: '',
            minDate: '',
            product: {},
            productDetail: {},
            productHistory: []
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
                withCredentials: true,
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

        addCart: function (id) {
            axios({
                method: "Get",
                url: host_context + "shopDispatcher/addCart",
                withCredentials: true,
                // crossDomain: true,
                params: {
                    id: id
                }
            })
                .then(function (value) {
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
                withCredentials: true,
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

getProductDetail();


function getProductDetail() {
    const ProductDetail_id = getDataFromSessionStorage("currentShopProductDetail_id");
    axios({
        method: "GET",
        url: host_context + "shopDispatcher/getProductDetail",
        withCredentials: true,
        params: { id: ProductDetail_id }
    })
        .then(function (value) {
            vm.productDetail = value.data;
            vm.product = vm.productDetail.product;
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
        withCredentials: true,
    })
        .then(function (value) {
            vm.productHistory = value.data;
            console.log("getProductHistory then");

        })
        .catch(function (e) {
            console.log("getProductHistory error " + e);
        });


}