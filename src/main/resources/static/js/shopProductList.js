import { host_context, nowDate } from './shopproductCommon.js';
import { saveDataToSessionStorage } from './shopproductCommon.js';

const vm = Vue.createApp({
    data() {
        return {
            currentType: 0,
            nowDate: '',
            minDate: '',
            products: []
        };
    },
    created() {
    },
    mounted() {
        axios({
            method: "GET",
            // url: "http://localhost:8080/MyShop/demo/getAllCouponActivity_json",
            url: host_context + "shopDispatcher/getAllProduct",
            withCredentials: true,
        })
            .then(function (value) {
                vm.products = value.data;
                console.log("getAllProduct then");

            })
            .catch(function (e) {
                console.log("getAllProduct error " + e);
            });
    },
    methods: {
        getAllProduct: function () {
            console.log('getAllProduct');
            axios({
                method: "GET",
                // url: "http://localhost:8080/MyShop/demo/getAllCouponActivity_json",
                url: host_context + "shopDispatcher/getAllProduct",
                withCredentials: true,
            })
                .then(function (value) {
                    vm.products = value.data;
                    console.log("getAllProduct then");

                })
                .catch(function (e) {
                    console.log("getAllProduct error " + e);
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
                    console.log("addFollow then");

                })
                .catch(function (e) {
                    console.log("addFollow error " + e);
                });


        },
        clickShopDetail: function (id) {
            saveDataToSessionStorage("currentShopProductDetail_id", id);
            // window.location.href = "./shopProductDetail.html";
        },
        getProductByType(type) {
            vm.currentType = type;
            axios({
                method: "Get",
                url: host_context + "shopDispatcher/getProductByType",
                withCredentials: true,
                // crossDomain: true,
                params: { type: type }
            },)
                .then(function (value) {
                    vm.products = value.data;

                    console.log("getProductByType then");

                })
                .catch(function (e) {
                    console.log("getProductByType error " + e);
                });
        },
        salseNumberBtn() {
            axios({
                method: "Get",
                url: host_context + "shopDispatcher/getProductByBuyTimes",
                withCredentials: true,
                // crossDomain: true,
                params: { type: vm.currentType }
            },)
                .then(function (value) {
                    vm.products = value.data;

                    console.log("salseNumberBtn then");

                })
                .catch(function (e) {
                    console.log("salseNumberBtn error " + e);
                });
        }

    },
}).mount(".shopmain");



const vm2 = Vue.createApp({
    data() {
        return {
            searchText: ''
        };
    },
    created() {

    },
    mounted() {

    },
    methods: {
        searchProducts() {
            console.log(vm2.searchText)
            axios({
                method: "Get",
                url: host_context + "shopDispatcher/searchProducts",
                withCredentials: true,
                // crossDomain: true,
                params: { search: vm2.searchText }
            },)
                .then(function (value) {
                    vm.products = value.data;

                    console.log("searchProducts then");

                })
                .catch(function (e) {
                    console.log("searchProducts error " + e);
                });
        }
    },
}).mount(".productline");



vm.getAllProduct();


