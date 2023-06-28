import { host_context, nowDate } from './shopproductCommon.js';
// import managerSideTemplate from "./managerSideTemplate.vue";
// import managerSideTemplate from "static/npb/js/managerSideTemplate.vue";
// {/* <script th:src="@{/static/npb/js/productManager.js}"></script> */ }

const vm = Vue.createApp({
    data() {
        return {
            mainguideContent: [{ id: 1, text: "商品管理", action: "manageProduct" },
            { id: 2, text: "商品新增", action: "addProduct" },
            { id: 3, text: "其他", action: "otherSetting" }],
            currentMainguideContent: 1,
            nowDate: '',
            minDate: '',
            newProduct: {},
            newProductImg: [],
            products: [],
            file: null,
            takeOffTime: ''
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
            getAllProduct();
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
        addProduct: function () {
            console.log("message: " + vm.message);
            console.log("newProduct: " + vm.newProduct);
            console.log("newProduct.product_name: " + vm.newProduct.product_name);
            console.log("newProduct.launch_time: " + vm.newProduct.launch_time);
            vm.newProduct.productImages = vm.newProductImg;
            let jsonProduct = JSON.stringify(vm.newProduct);
            axios({
                method: "POST",
                url: host_context + "shopDispatcher/addProduct",
                data: {
                    newProduct: vm.newProduct
                }
            })
                .then(function (value) {
                    // vm.products = value.data;


                    console.log("addProduct then");

                })
                .catch(function (e) {
                    console.log("addProduct error " + e);
                });


        },
        takeOffProduct: function (id) {
            console.log('takeOffProduct');
            axios({
                method: "GET",
                url: host_context + "shopDispatcher/takeOffProduct",
                params: {
                    id: id
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
        takeOnProduct: function (id) {
            console.log('takeOnProduct');
            axios({
                method: "GET",
                url: host_context + "shopDispatcher/takeOnProduct",
                params: {
                    id: id
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
        imgSubmit: function () {
            console.log('imgSubmit');
            const formData = new FormData();
            formData.append('file', this.file);

            // axios.post(host_context + "shopDispatcher/uploadProduct", formData, {
            //     headers: {
            //         'Content-Type': 'multipart/form-data'
            //     }
            // })
            //     .then(response => {
            //         console.log('File uploaded successfully!');
            //     })
            //     .catch(error => {
            //         console.error('Error uploading file:', error);
            //     });

            axios({
                method: "POST",
                headers: {
                    'Content-Type': 'multipart/form-data'
                },
                url: host_context + "shopDispatcher/uploadProduct",
                data: formData

            })
                .then(function (value) {
                    // vm.getallproduct();
                    console.log("imgSubmit then");
                })
                .catch(function (e) {
                    console.log("imgSubmit error " + e);
                });
        },
        onFileChange(event) {
            this.file = event.target.files[0];
        },
        changeMainContent(action) {
            vm.currentMainguideContent = action;
            console.log("action " + action);
        },
        onFileChange(e) {
            let file = e.files[0];
            let reader = new FileReader();
            reader.readAsDataURL(file);
            reader.addEventListener("load", function () {
                let index = vm.newProductImg.length;
                vm.newProductImg.push({ id: index, image: reader.result, name: file.name });
            });
        },
        pageBtn(pageIndex) {
            axios({
                method: "GET",
                url: host_context + "shopDispatcher/getSomeProduct",
                params: {
                    "pageIndex": pageIndex
                }
            })
                .then(function (value) {
                    console.log("getSomeProduct then");
                })
                .catch(function (e) {
                    console.log("getSomeProduct error " + e);
                });
        },
        updateProductInfo(index) {
            let product = vm.products[index];
            axios({
                method: "GET",
                url: host_context + "shopDispatcher/updateProductInfo",
                params: {
                    product: product
                }
            })
                .then(function (value) {
                    console.log("updateProductInfo then");
                })
                .catch(function (e) {
                    console.log("updateProductInfo error " + e);
                });
        },


    },
}).mount("#vue-body");

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



function getAllProduct() {
    console.log('getAllProduct');
    axios({
        method: "GET",
        // url: "http://localhost:8080/MyShop/demo/getallproduct_json",
        url: host_context + "shopDispatcher/getAllProduct",
        params: {
            product: "",
            productImg: ""
        }
    })
        .then(function (value) {
            vm.products = value.data;

            console.log("getallproduct then");
        })
        .catch(function (e) {
            console.log("getallproduct error " + e);
        });
}