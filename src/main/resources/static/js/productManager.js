import { host_context, nowDate } from './shopproductCommon.js';

//    //狀態 {0：新增  ; 1:上架 ; 2 :排定上架; 11 :下架;12 :排定下架}
// /商品種類 { 0: NS主機; 1: NS手把; 2: NS卡帶; 3: NS周邊;
//     // 10: XBOX主機; 11: XBOX手把; 12: XBOX遊戲片; 13: XBOX周邊;
//     // 20: PS主機; 21: PS手把; 22: PS遊戲片; 23: PS周邊 }   { 2: NS遊戲片, 12: XBOX遊戲片, 22: PS遊戲片 },
const productState = {};
const vm = Vue.createApp({
    data() {
        return {
            enumProductType: [{ type: 2, content: "NS遊戲片" }, { type: 12, content: "XBOX遊戲片" }, { type: 22, content: "PS遊戲片" }],
            enumProductState: { new: 0, takeOn: 1, takeOning: 2, takeOff: 11, takeOffing: 12 },
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
            takeOffTime: '',
            productSelectAction: ['排定上架', '排定下架', "移除排定上架", "移除排定下架"],
            currentProductSelectAction: '',
            limitNumOfSelect: [5, 10, 15, 20],
            limitNum: 5
        };
    },
    methods: {
        getallproduct: function () {
            getAllProduct();
        },
        createProductFromcsv: function () {
            console.log('createProductFromcsv');
            axios({
                method: "GET",
                url: host_context + "shopDispatcher/createProductFromcsv",
            })
                .then(function (value) {


                    vm.getallproduct();
                    console.log("createProductFromcsv then");
                })
                .catch(function (e) {
                    console.log("createProductFromcsv error " + e);
                });
        },
        addProduct: function () {
            // console.log("message: " + vm.message);
            // console.log("newProduct: " + vm.newProduct);
            // console.log("newProduct.product_name: " + vm.newProduct.product_name);
            // console.log("newProduct.launch_time: " + vm.newProduct.launch_time);
            vm.newProduct.productImages = vm.newProductImg;

            if (vm.newProduct.type == null) {
                vm.newProduct.type = vm.enumProductType[0].type;
            }

            let jsonProduct = JSON.stringify(vm.newProduct);
            axios({
                method: "POST",
                url: host_context + "shopDispatcher/addProduct",
                data: {
                    newProduct: vm.newProduct
                }
            })
                .then(function (value) {
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
            let product = Object.assign({}, vm.products[index]);

            const dateObject = new Date(product.launchTime);
            const formattedDateTime = dateObject.toLocaleString('en-US', {
                timeZone: 'Asia/Taipei',
                month: 'short',
                day: 'numeric',
                year: 'numeric',
                hour: 'numeric',
                minute: 'numeric',
                second: 'numeric',
                hour12: true
            });
            product.launchTime = formattedDateTime;

            if (product.takeoffTime != null) {

                const dateObject = new Date(product.takeoffTime);
                const formattedDateTime = dateObject.toLocaleString('en-US', {
                    timeZone: 'Asia/Taipei',
                    month: 'short',
                    day: 'numeric',
                    year: 'numeric',
                    hour: 'numeric',
                    minute: 'numeric',
                    second: 'numeric',
                    hour12: true
                });

                product.takeoffTime = formattedDateTime;
            }

            axios({
                method: "POST",
                url: host_context + "shopDispatcher/updateProductInfo",
                data: {
                    product,
                }
            })
                .then(function (value) {
                    console.log("updateProductInfo then");
                })
                .catch(function (e) {
                    console.log("updateProductInfo error " + e);
                });
        },
        updateLaunchTime(item, value) {
            item.launchTime = value;
        },

        updatetakeOffTime(item, value) {
            item.takeoffTime = value;
        },
        editText(e) {
            e.target.setAttribute('contenteditable', true);
            e.target.focus();
        },
        productSelectActionchange(index, action) {
            console.log(action);
            vm.products[index].currentProductSelectAction = action;
            // vm.currentProductSelectAction = action;
        },
        btnAction(index) {
            let id = vm.products[index].id;
            // { 排定上架,排定下架,移除排定上架,移除排定下架  }
            switch (vm.products[index].currentProductSelectAction) {
                case '排定上架':
                    productOperate('takeOnProduct', id);
                    break;
                case '排定下架':
                    productOperate('takeOffProduct', id);
                    break;
                case '移除排定上架':
                    productOperate('removeTakeOningProduct', id);
                    break;
                case '移除排定下架':
                    productOperate('removeTakeOffingProduct', id);
                    break;
            }
        },
        limitNumOfSelectChanre(limit) {
            vm.limitNum = parseInt(limit);
            getAllProduct();
        },
        changeProductType(product, type) {
            product.type = type;
        }

    },
}).mount("#vue-body");

vm.getallproduct();


vm.nowDate = nowDate;
vm.minDate = vm.nowDate;
vm.newProduct.launch_time = vm.nowDate;

function getAllProduct() {
    console.log('getAllProduct');

    let sqlConditions = [];
    let conditions = [];

    sqlConditions.push({ "key": "limit", "value": vm.limitNum });
    sqlConditions.push({ "key": "offset", "value": 0 });
    // conditions.push({ "key": "type", "value": 2 });

    let object = {
        "msg": "getA",
        "conditions": conditions,
        "sqlConditions": sqlConditions
    }

    let jsonObject = JSON.stringify(object);
    let encodedJsonObject = encodeURIComponent(jsonObject);
    axios({
        method: "Get",
        url: host_context + "shopDispatcher/getAllProductByCondition",
        withCredentials: true,
        crossDomain: true,
        params: {
            params: encodedJsonObject
        }
    })
        .then(function (value) {
            let products_ = value.data;
            products_.forEach(element => {
                let originalDate = element.launchTime;
                let dateObject = new Date(originalDate);
                let showLaunchTime = dateObject.toLocaleString('zh-TW', { TimeZone: 'Asia/Taipei' });
                dateObject.setHours(dateObject.getHours() + 8);
                let formattedDateTime = dateObject.toISOString().slice(0, 16);
                element.launchTime = formattedDateTime;
                element.showLaunchTime = showLaunchTime;

                if (element.takeoffTime != null) {
                    dateObject = new Date(element.takeoffTime);
                    let showTakeoffTime = dateObject.toLocaleString('zh-TW', { TimeZone: 'Asia/Taipei' });
                    dateObject.setHours(dateObject.getHours() + 8);
                    formattedDateTime = dateObject.toISOString().slice(0, 16);
                    element.takeoffTime = formattedDateTime;
                    element.showTakeoffTime = showTakeoffTime;
                }

                ////    //狀態 {0：新增  ; 1:已上架 ; 2 :已排定上架; 11 :已下架;12 :已排定下架}
                switch (element.state) {
                    case 0:
                        element.currentProductState = '新增';
                        element.currentProductSelectAction = '排定上架';
                        break;
                    case 1:
                        element.currentProductState = '已上架';
                        break;
                    case 2:
                        element.currentProductState = '已排定上架';
                        break;
                    case 11:
                        element.currentProductState = '已下架';
                        break;
                    case 12:
                        element.currentProductState = '已排定下架';
                        break;
                }

                //enumProductType


            });

            // { 排定上架,排定下架,移除排定上架,移除排定下架  }

            vm.products = products_;

            console.log("getallproduct then");
        })
        .catch(function (e) {
            console.log("getallproduct error " + e);
        });
}


function productOperate(action, id) {
    console.log('productOperate');
    axios({
        method: "GET",
        url: host_context + "shopDispatcher/" + action,
        params: {
            id: id
        }
    })
        .then(function (value) {
            console.log("productOperate then");

        })
        .catch(function (e) {
            console.log("productOperate error " + e);
        });
}

