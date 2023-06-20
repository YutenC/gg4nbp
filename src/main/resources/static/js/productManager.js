import { host_context, nowDate } from './shopproductCommon.js';
// import managerSideTemplate from "./managerSideTemplate.vue";
// import managerSideTemplate from "static/npb/js/managerSideTemplate.vue";
// {/* <script th:src="@{/static/npb/js/productManager.js}"></script> */ }
const vm = Vue.createApp({
    data() {
        return {
            nowDate: '',
            minDate: '',
            newProduct: {},
            products: [],
        };
    },
    components: {
        // 'shared-content': managerSideTemplate
        // 'template-example': { template: '#template-example' },
        // 'template-example': {
        //     template: `<div>1234</div>`
        // }
        'my-template': { template: '#my-template' }
    },
    // template: '#template-example',
    // template: '#my-template',
    methods: {
        getallproduct: function () {
            console.log('getAllProduct');
            axios({
                method: "GET",
                // url: "http://localhost:8080/MyShop/demo/getallproduct_json",
                url: host_context + "shopDispatcher/getAllProduct",
            })
                .then(function (value) {
                    vm.products = value.data;

                    console.log("getallproduct then");
                })
                .catch(function (e) {
                    console.log("getallproduct error " + e);
                });
        },
        createProductFromcsv: function () {
            console.log('createProductFromcsv');
            axios({
                method: "GET",
                url: host_context + "shopDispatcher/createProductFromcsv",
                // url: host_context + "shopDispatcher/longTimeProcess",
            })
                .then(function (value) {

                    // if ("longTime" === value.data.state) {
                    //     console.log(value.data.msg);

                    //     setTimeout(function () {
                    //         vm.getBackgroundMessage();
                    //     }, 1000);
                    // }



                    vm.getallproduct();
                    console.log("createProductFromcsv then");
                })
                .catch(function (e) {
                    console.log("createProductFromcsv error " + e);
                });
        },
        takeOnProduct: function () {
            console.log("message: " + vm.message);
            console.log("newProduct: " + vm.newProduct);
            console.log("newProduct.product_name: " + vm.newProduct.product_name);
            console.log("newProduct.launch_time: " + vm.newProduct.launch_time);
            let jsonProduct = JSON.stringify(vm.newProduct);
            axios({
                method: "POST",
                url: "http://localhost:8080/MyShop/demo/takeOnProduct",
                params: {
                    newProduct: jsonProduct
                }
            })
                .then(function (value) {
                    // vm.products = value.data;


                    console.log("takeOnProduct then");

                })
                .catch(function (e) {
                    console.log("takeOnProduct error " + e);
                });


        },
        takeOffProduct: function (id) {
            console.log('takeOffProduct');
            axios({
                method: "GET",
                url: "http://localhost:8080/MyShop/demo/takeOffProduct",
                params: {
                    product_id: id
                }
            })
                .then(function (value) {
                    // vm.products = value.data;


                    console.log("takeOffProduct then");

                })
                .catch(function (e) {
                    console.log("takeOffProduct error " + e);
                });
        },
        getBackgroundMessage: function () {
            console.log('getBackgroundMessage');
            axios({
                method: "GET",
                // url: host_context + "shopDispatcher/createProductFromcsv",
                url: host_context + "shopDispatcher/getBackgroundMessage",
                params: {
                    taskName: "readCSV"
                }
            })
                .then(function (value) {

                    if ("longTime" === value.data.state) {
                        console.log(value.data.msg);
                        setTimeout(function () {
                            vm.getBackgroundMessage();
                        }, 1000);
                    }

                    // vm.getallproduct();
                    console.log("getBackgroundMessage then");
                })
                .catch(function (e) {
                    console.log("getBackgroundMessage error " + e);
                });
        },

    },
}).mount("#page-top");

vm.getallproduct();

// const currentDate = new Date();
// const year = currentDate.getFullYear();
// const month = String(currentDate.getMonth() + 1).padStart(2, '0');
// const day = String(currentDate.getDate()).padStart(2, '0');

// vm.nowDate = `${year}-${month}-${day}`;

vm.nowDate = nowDate;
vm.minDate = vm.nowDate;
vm.newProduct.launch_time = vm.nowDate;

// function timer() {
//     i++;
//     self.postMessage(i);

//     setTimeout(function () {
//         timesCount();
//     }, 1000);
// }
// timesCount();

// Vue.component('shared-content', managerSideTemplate);