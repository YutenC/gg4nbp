import { host_context, nowDate } from './shopproductCommon.js';

const vm = Vue.createApp({
    data() {
        return {
            currentMainguideContent: 1,
            mainguideContent: [{ id: 1, text: "折價券管理", action: "manageCoupon" },
            { id: 2, text: "折價券新增", action: "addCoupon" },
            { id: 3, text: "其他", action: "otherSetting" }],
            nowDate: '',
            minDate: '',
            newCouponActivity: {},
            newCoupon: {},
            newcouponActivity: {},
            couponActivity: [],
            coupon: [],
            AllCouponActivityRequest: {},
            showCouponActivity: true,
            couponMembers: [],
            longTimeAction: '',
            sendEmailTime: '',
            sendEmailState: ""
        };
    },
    methods: {
        getAllCouponActivity: function () {
            console.log('getproduct');
            axios({
                method: "GET",
                // url: "http://localhost:8080/MyShop/demo/getAllCouponActivity_json",
                url: host_context + "shopDispatcher/getAllCouponActivity",
                // params: {
                //     pro_id: 2
                // }
            })
                .then(function (value) {
                    vm.AllCouponActivityRequest = value.data;
                    let state = value.data.state;
                    if (state === "success") {
                        vm.showCouponActivity = true;
                        vm.couponActivity = value.data.content;
                        console.log("vm.couponActivity " + vm.couponActivityf);
                        for (let i = 0; i < vm.couponActivity.length; i++) {
                            console.log(vm.couponActivity[i].coupon);
                            let json = JSON.parse(vm.couponActivity[i].coupon);
                            vm.coupon.push(json);

                            vm.couponActivity[i].coupon = json;
                        }
                    }
                    else {
                        vm.showCouponActivity = false;
                        console.log(value.data.content);

                    }





                    // if (!value.data.includes("error")) {
                    //     vm.couponActivity = value.data;
                    //     console.log("vm.couponActivity " + vm.couponActivityf);
                    //     for (let i = 0; i < vm.couponActivity.length; i++) {
                    //         console.log(vm.couponActivity[i].coupon);
                    //         let json = JSON.parse(vm.couponActivity[i].coupon);
                    //         vm.coupon.push(json);

                    //         vm.couponActivity[i].coupon = json;
                    //     }
                    // }
                    // else {
                    //     let state = value.data.state;
                    //     if (state === "success") {

                    //     }
                    //     else {

                    //         console.log(value.data.content);

                    //     }
                    // }





                    console.log("getAllCouponActivity then");

                })
                .catch(function (e) {
                    console.log("getAllCouponActivity error " + e);
                });
        },
        updateCouponActivity: function (id) {
            console.log("updateCouponActivity: id " + id);
            // vm.newcouponActivity.coupon = vm.newCoupon;

            let singlecouponActivity;
            vm.couponActivity.forEach(element => {
                if (element.coupon.id == id) {
                    singlecouponActivity = element;
                }
            });

            // let singlecouponActivity = vm.couponActivity[id]
            let jsonnewcouponActivity = JSON.stringify(singlecouponActivity);
            axios({
                method: "POST",
                // url: "http://localhost:8080/MyShop/demo/deleteCoupon",
                url: host_context + "shopDispatcher/updateCouponActivity",
                params: {
                    couponId: id,
                    newCouponActivity: jsonnewcouponActivity
                }
            })
                .then(function (value) {
                    console.log("updateCouponActivity then ");
                    vm.getAllCouponActivity();
                })
                .catch(function (e) {
                    console.log("updateCouponActivity error " + e);
                });

        },
        deleteCouponActivity: function (id) {
            console.log("deleteCouponActivity: id " + id);
            axios({
                method: "GET",
                // url: "http://localhost:8080/MyShop/demo/deleteCoupon",
                url: host_context + "shopDispatcher/deleteCoupon",
                params: {
                    couponId: id
                }
            })
                .then(function (value) {
                    console.log("deleteCouponActivity then ");
                    vm.getAllCouponActivity();
                })
                .catch(function (e) {
                    console.log("deleteCouponActivity error " + e);
                });

        },
        addCouponActivity: function () {
            vm.newcouponActivity.coupon = vm.newCoupon;
            let jsonnewcouponActivity = JSON.stringify(vm.newcouponActivity);
            axios({
                method: "POST",
                // url: "http://localhost:8080/MyShop/nbpShop/addCouponActivity",
                url: host_context + "shopDispatcher/addCouponActivity",
                params: {
                    newCouponActivity: jsonnewcouponActivity
                }
            })
                .then(function (value) {
                    console.log("addCouponActivity then");
                })
                .catch(function (e) {
                    console.log("addCouponActivity error " + e);
                });
        },
        autoGenerateCouponActivity: function () {
            axios({
                method: "GET",
                url: host_context + "shopDispatcher/autoGenerateCouponActivity",
            })
                .then(function (value) {
                    vm.getAllCouponActivity();
                    console.log("autoGenerateCouponActivity then");

                })
                .catch(function (e) {
                    console.log("autoGenerateCouponActivity error " + e);
                });


        },
        changeMainContent(action) {
            vm.currentMainguideContent = action;
            console.log("action " + action);
        },
        getCouponMemberInfo() {
            axios({
                method: "GET",
                url: host_context + "shopDispatcher/getCouponMemberInfo",
            })
                .then(function (value) {
                    let temp = value.data;

                    temp.forEach(element => {
                        element.check = false;
                    });

                    vm.couponMembers = temp;
                    console.log("getCouponMemberInfo then");

                })
                .catch(function (e) {
                    console.log("getCouponMemberInfo error " + e);
                });
        },
        sendEmail(action) {
            console.log(vm.couponMembers)



            switch (action) {
                case 0://立即發送
                    vm.sendEmailState = "傳送中";
                    break;
                case 1://根據時間
                    //
                    const dateObject = new Date(vm.sendEmailTime);
                    const formattedDateTime = dateObject.toLocaleDateString('en-us', {
                        month: 'short',
                        day: 'numeric',
                        year: 'numeric',
                        hour: 'numeric',
                        minute: 'numeric',
                        second: 'numeric',
                        hour12: true
                    });

                    vm.couponMembers.forEach(element => {
                        if (element.check) {
                            element.sendEmailTime = formattedDateTime;
                        }


                    });
                    vm.sendEmailState = "";
                    break;
            }

            let object = { "action": action, "couponMembers": vm.couponMembers };
            const jsonObject = JSON.stringify(object);
            const encodeObj = encodeURI(jsonObject);

            axios({
                method: "GET",
                url: host_context + "shopDispatcher/sendEmail",
                params: {
                    params: encodeObj
                }
            })
                .then(function (value) {
                    let temp = value.data;

                    switch (temp.state) {
                        case "longTime":
                            vm.longTimeAction = temp.msg;
                            setTimeout(function () {
                                getBackgroundMessage();
                            }, 1000);
                            break;
                        case "ok":
                            vm.sendEmailState = "完成時間設定";
                            break;
                    }
                    console.log("sendEmail then");

                })
                .catch(function (e) {
                    console.log("sendEmail error " + e);
                });
        }
    },
}).mount("#vue-body");

// vm.$data.message = 'SSSSS';
vm.getAllCouponActivity();

// const currentDate = new Date();
// const year = currentDate.getFullYear();
// const month = String(currentDate.getMonth() + 1).padStart(2, '0');
// const day = String(currentDate.getDate()).padStart(2, '0');
// vm.nowDate = `${year}-${month}-${day}`;

vm.nowDate = nowDate;
vm.minDate = vm.nowDate;
// vm.newcouponActivity.coupon.deadline = vm.nowDate;


function getBackgroundMessage() {
    console.log('getBackgroundMessage');
    axios({
        method: "GET",
        url: host_context + "shopDispatcher/getBackgroundMessage",
        params: {
            taskName: vm.longTimeAction
        }
    })
        .then(function (value) {

            if ("longTime" === value.data.state) {
                console.log(value.data.msg);
                setTimeout(function () {
                    getBackgroundMessage();
                }, 1000);
            }
            else {
                vm.sendEmailState = "傳送完成";
                console.log("longTime process OK");
            }

            console.log("getBackgroundMessage then");
        })
        .catch(function (e) {
            console.log("getBackgroundMessage error " + e);
        });
}