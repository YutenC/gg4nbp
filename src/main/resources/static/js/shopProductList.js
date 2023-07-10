import { host_context, nowDate } from './shopproductCommon.js';
import { saveDataToSessionStorage, getURLSearch } from './shopproductCommon.js';

let pageCurrentType = -1;
let enumPageCurrentType = { ALL: -1, NS: 2, PS: 22, XBOX: 12 };
let enumProductState = { new: 0, takeOn: 1, takeOning: 2, takeOff: 11, takeOffing: 12 }

const sidebar = Vue.createApp({
    data() {
        return {
            enumProductState: { new: 0, takeOn: 1, takeOning: 2, takeOff: 11, takeOffing: 12 },
            enumPageCurrentType: enumPageCurrentType,
        }
    },
    methods: {
        getProductByType(type) {
            pageCurrentType = type;

            let conditions = [];
            let required = [];
            if (pageCurrentType != -1) {
                conditions.push({ key: "type", value: pageCurrentType });
            }
            conditions.push({ key: "state", value: enumProductState.takeOn }, { key: "state", value: enumProductState.takeOffing });
            required.push("productIndexImage", "follow");

            let params = { msg: "getAllProduct", "conditions": conditions, "required": required };

            let jsonObject = JSON.stringify(params);
            let encodeObject = encodeURIComponent(jsonObject);


            axios({
                method: "Get",
                // url: host_context + "shopDispatcher/getProductByType",
                url: host_context + "shopDispatcher/getAllProductByCondition",
                // withCredentials: true,
                // crossDomain: true,
                // params: { type: type }
                params: { params: encodeObject }
            },)
                .then(function (value) {
                    vm.products = value.data;

                    console.log("getProductByType then");

                })
                .catch(function (e) {
                    console.log("getProductByType error " + e);
                });
        },
    }
}).mount('.sidebar');

const vm = Vue.createApp({
    data() {
        return {
            nowDate: '',
            minDate: '',
            products: [],
            followLists: [],
            productHistory: [],
            isHistoryAreaHidden: true
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
        checkLogin();
    },
    methods: {
        getAllProduct: function () {
            getAllProduct();
        },
        addCart: function (action, id) {
            let transObj = { productId: id, buyAmount: 1 };
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

                    Toast.fire({
                        icon: 'success',
                        title: '已加入購物車'
                    });
                })
                .catch(function (e) {
                    console.log("addCart error " + e);
                });
        },
        // addCart: function (id) {
        //     let transObj = { productId: id, buyAmount: 1 };
        //     axios({
        //         method: "Post",
        //         url: host_context + "ShoppingList",
        //         // withCredentials: true,
        //         // crossDomain: true,
        //         params: {
        //             demand: "addOneShoppingList",
        //             transObj: JSON.stringify(transObj),
        //         }
        //     })
        //         .then(function (response) {
        //             let result = response.data;
        //             if (result != null && result.redirect) {
        //                 window.location.href = "./member_login.html";
        //             }
        //             else {

        //             }


        //             // let result = response.value;
        //             // if (!result.successful) {
        //             //     window.location.href = "./member_login.html";
        //             // }
        //             // else {

        //             // }

        //             // console.log("addCart then");

        //         })
        //         .catch(function (e) {
        //             console.log("addCart error " + e);
        //         });


        // },
        // addCart: function (id) {
        //     axios({
        //         method: "Get",
        //         url: host_context + "shopDispatcher/addCart",
        //         // withCredentials: true,
        //         // crossDomain: true,
        //         params: {
        //             id: id,
        //             redirectUrl: "http://localhost:8080/gg4nbp/shop/shopProductList.html"
        //         }
        //     })
        //         .then(function (response) {

        //             if (response.data.state === "redirect") {
        //                 console.log(response.data.msg);
        //                 window.location.href = response.data.msg;
        //             }

        //             // // Check if the response status is a redirect (3xx)
        //             // if (response.status >= 300 && response.status < 400) {
        //             //     const redirectUrl = response.headers.location;
        //             //     // Perform your desired action with the redirect URL
        //             //     console.log('Redirect URL:', redirectUrl);
        //             // } else {
        //             //     // Handle the non-redirect response
        //             //     console.log('Response:', response.data);
        //             // }

        //             console.log("addCart then");

        //         })
        //         .catch(function (e) {
        //             console.log("addCart error " + e);
        //         });


        // },
        addFollow: function (index, id) {

            axios({
                method: "Get",
                url: host_context + "shopDispatcher/addFollow",
                // withCredentials: true,
                // crossDomain: true,
                params: {
                    id: id,
                    redirectUrl: "http://localhost:8080/gg4nbp/shop/shopProductList.html"
                }
            })
                .then(function (value) {
                    let result = value.data;

                    if (result.state.toLowerCase() === "redirect") {
                        console.log(result.msg);
                        window.location.href = result.msg;
                    }
                    else if (result.state.toLowerCase() === "ok") {
                        vm.products[index].follow = result.content;
                    }

                    if (vm.products[index].follow === 0) {
                        Toast.fire({
                            icon: 'error',
                            title: '已從追蹤清單移除'
                        })
                    } else {
                        Toast.fire({
                            icon: 'info',
                            title: '已加入我的追蹤清單'
                        })
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
        // historymouseenter: function () {
        //     vm.isHistoryAreaHidden = false;
        // },
        // historymouseleave() {
        //     vm.isHistoryAreaHidden = true;
        // }

    }
}).mount(".productShowcase");




const vm2 = Vue.createApp({
    data() {
        return {
            searchText: '',
            manyToless: true
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
            conditions.push({ key: "state", value: enumProductState.takeOn }, { key: "state", value: enumProductState.takeOffing });
            conditions.push({ action: "like", key: "productName", value: vm2.searchText });

            required.push("productIndexImage", "follow");

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
        },
        salseNumberBtn() {
            let conditions = [];
            let required = [];

            if (pageCurrentType != -1) {
                conditions.push({ action: "=", key: "type", value: pageCurrentType });
            }
            conditions.push({ key: "state", value: enumProductState.takeOn }, { key: "state", value: enumProductState.takeOffing });
            conditions.push({ action: "like", key: "productName", value: vm2.searchText });


            let sort = { action: "order", key: "DESC", value: "buyTimes" };//DESC
            if (vm2.manyToless) {
                sort = { action: "order", key: "DESC", value: "buyTimes" };//DESC
                // vm2.manyToless = false;
            }
            else {
                sort = { action: "order", key: "", value: "buyTimes" };//DESC
                // vm2.manyToless = true;
            }


            required.push("productIndexImage", "follow");

            let params = { msg: "salseNumberBtn", "conditions": conditions, "required": required, "sort": sort };
            let jsonObject = JSON.stringify(params);
            let encodeObject = encodeURIComponent(jsonObject);
            axios({
                method: "Get",
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
        },
    },
}).mount(".innerFunc");



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
                        getAllProduct();
                        console.log("memberLogoutServlet then");
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



// vm.getAllProduct();

function getAllProduct() {

    let conditions = [];
    let required = [];
    if (pageCurrentType != -1) {
        conditions.push({ key: "type", value: pageCurrentType });
    }
    conditions.push({ key: "state", value: enumProductState.takeOn }, { key: "state", value: enumProductState.takeOffing });
    required.push("productIndexImage", "follow");

    let params = { msg: "getAllProduct", "conditions": conditions, "required": required };

    let jsonObject = JSON.stringify(params);
    let encodeObject = encodeURIComponent(jsonObject);

    axios({
        method: "GET",
        url: host_context + "shopDispatcher/getAllProductByCondition",
        // withCredentials: true,
        params: { params: encodeObject }
    })
        .then(function (value) {
            vm.products = value.data;
            getProductHistory()
            // getFollows();
            console.log("getAllProduct then");

        })
        .catch(function (e) {
            console.log("getAllProduct error " + e);
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