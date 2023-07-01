import { host_context, nowDate } from './shopproductCommon.js';
import { saveDataToSessionStorage, getURLSearch } from './shopproductCommon.js';

let pageCurrentType = -1;
let enumPageCurrentType = { NS: 2, PS: 22, XBOX: 12 };
const vm = Vue.createApp({
    data() {
        return {
            enumPageCurrentType: enumPageCurrentType,
            nowDate: '',
            minDate: '',
            products: [],
            followLists: []
        };
    },
    created() {

    },
    mounted() {
        let urlSearch = getURLSearch();
        urlSearch.forEach(element => {
            if (element.key == "type") {
                pageCurrentType = enumPageCurrentType[element.value];
            }
        });
        getAllProduct();
    },
    methods: {
        getAllProduct: function () {
            getAllProduct();
        },

        addCart: function (id) {
            axios({
                method: "Get",
                url: host_context + "shopDispatcher/addCart",
                // withCredentials: true,
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
        addFollow: function (index, id) {
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
                        vm.products[index].follow = result.content;
                    }


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
            pageCurrentType = type;
            axios({
                method: "Get",
                url: host_context + "shopDispatcher/getProductByType",
                // withCredentials: true,
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
            let conditions = [];
            let required = [];

            if (pageCurrentType != -1) {
                conditions.push({ action: "=", key: "type", value: pageCurrentType });
            }
            conditions.push({ action: "like", key: "productName", value: vm2.searchText });

            let sort = { action: "order", key: "", value: "buyTimes" };//DESC

            required.push("productIndexImage");

            let params = { msg: "salseNumberBtn", "conditions": conditions, "required": required, "sort": sort };
            let jsonObject = JSON.stringify(params);
            let encodeObject = encodeURIComponent(jsonObject);
            axios({
                method: "Get",
                // url: host_context + "shopDispatcher/getProductByBuyTimes",
                url: host_context + "shopDispatcher/getAllProductByCondition",
                // withCredentials: true,
                // crossDomain: true,
                // params: { type: pageCurrentType }
                params: { params: encodeObject }
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

            let conditions = [];
            let required = [];
            if (pageCurrentType != -1) {
                conditions.push({ action: "=", key: "type", value: pageCurrentType });
            }
            conditions.push({ action: "like", key: "productName", value: vm2.searchText });

            required.push("productIndexImage");

            let params = { msg: "searchProducts", "conditions": conditions, "required": required };
            let jsonObject = JSON.stringify(params);
            let encodeObject = encodeURIComponent(jsonObject);
            axios({

                method: "Get",
                // url: host_context + "shopDispatcher/searchProducts",
                url: host_context + "shopDispatcher/getAllProductByCondition",
                // withCredentials: true,
                // crossDomain: true,
                // params: { search: vm2.searchText }
                params: { params: encodeObject }
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



// vm.getAllProduct();

function getAllProduct() {

    let conditions = [];
    let required = [];
    if (pageCurrentType != -1) {
        conditions.push({ key: "type", value: pageCurrentType });
    }

    required.push("productIndexImage");

    let params = { msg: "getAllProduct", "conditions": conditions, "required": required };

    let jsonObject = JSON.stringify(params);
    let encodeObject = encodeURIComponent(jsonObject);

    axios({
        method: "GET",
        // url: "http://localhost:8080/MyShop/demo/getAllCouponActivity_json",
        // url: host_context + "shopDispatcher/getAllProductWithIndexImg",
        url: host_context + "shopDispatcher/getAllProductByCondition",
        // withCredentials: true,
        params: { params: encodeObject }
    })
        .then(function (value) {
            vm.products = value.data;

            // getFollows();
            console.log("getAllProduct then");

        })
        .catch(function (e) {
            console.log("getAllProduct error " + e);
        });

}

function getFollows() {
    console.log(getFollows)
    axios({
        method: "Get",
        url: host_context + "shopDispatcher/getFollowByMemberId",
        // withCredentials: true,
        // crossDomain: true,
        params: { memberId: vm2.searchText }
    },)
        .then(function (value) {
            vm.followLists = value.data;

            console.log("searchProducts then");

        })
        .catch(function (e) {
            console.log("searchProducts error " + e);
        });
}


