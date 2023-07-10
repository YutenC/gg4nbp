import { host_context, nowDate } from './shopproductCommon.js';

//狀態 {0：新增/未發佈  ; 1:有效/已發佈 ; 2 :失效; }

const vm = Vue.createApp({
    data() {
        return {
            enumCouponState: { unpublish: 0, publish: 1, failed: 2 },
            enumCouponStateInfo: ["未發佈", "發佈", "失效"],
            currentMainguideContent: 1,
            mainguideContent: [{ id: 1, text: "折價券管理", action: "manageCoupon" },
            { id: 2, text: "折價券新增", action: "addCoupon" },
            { id: 3, text: "發送折價券", action: "otherSetting" }],
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
            sendEmailState: "",
            limitNumOfSelect: [5, 10, 15, 20, 100],
            limitNum: "20",
            selectedCoupon: '',
            initSelectedCoupon: ''
        };
    },
    methods: {
        getAllCouponActivity: function () {
            getAllCouponActivity();
        },
        updateCouponActivity: function (couponActivity) {

            let updateCoupon = couponActivity.coupon;

            if (typeof updateCoupon.showError_discount === 'undefined') {
                updateCoupon.showError_discount = true;
            }

            if (typeof updateCoupon.showError_conditionPrice === 'undefined') {
                updateCoupon.showError_conditionPrice = true;
            }
            let flag = updateCoupon.showError_discount | updateCoupon.showError_conditionPrice |
                typeof updateCoupon.showError_discount === 'undefined' |
                typeof updateCoupon.showError_conditionPrice === 'undefined';
            if (flag) {
                return;
            }



            let coupon_ = Object.assign({}, couponActivity.coupon);

            const date = new Date(coupon_.deadline);
            const formatDate = date.toLocaleString('en-US', {
                timeZone: 'Asia/Taipei',
                month: 'short',
                day: 'numeric',
                year: 'numeric',
                hour: 'numeric',
                minute: 'numeric',
                second: 'numeric',
                hour12: true
            });
            // const formatDate = date.toLocaleString('en-US', { timeZone: 'Asia/Singapore', hour12: true });


            coupon_.deadline = formatDate;
            couponActivity.coupon = coupon_;

            let jsonnewcouponActivity = JSON.stringify(couponActivity);
            axios({
                method: "POST",
                url: host_context + "shopDispatcher/updateCouponActivity",
                params: {
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

            if (typeof vm.newCoupon.showError_discount === 'undefined') {
                vm.newCoupon.showError_discount = true;
            }

            if (typeof vm.newCoupon.showError_conditionPrice === 'undefined') {
                vm.newCoupon.showError_conditionPrice = true;
            }
            let flag = vm.newCoupon.showError_discount | vm.newCoupon.showError_conditionPrice |
                typeof vm.newCoupon.showError_discount === 'undefined' |
                typeof vm.newCoupon.showError_conditionPrice === 'undefined';
            if (flag) {
                return;
            }



            vm.newcouponActivity.coupon = vm.newCoupon;
            let jsonnewcouponActivity = JSON.stringify(vm.newcouponActivity);
            axios({
                method: "POST",
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
            if (vm.currentMainguideContent == 3) {
                for (let i = 0; i < vm.couponActivity.length; i++) {
                    if (vm.couponActivity[i].coupon.state == vm.enumCouponState.publish) {
                        vm.initSelectedCouponIndex = i;
                        vm.selectedCoupon = vm.couponActivity[i].coupon.discountCode;
                        break;
                    }
                }

            }

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
                    vm.sendEmailState = "已取得會員資訊";
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
                    if (vm.sendEmailTime === "") {
                        vm.sendEmailState = "時間格式錯誤";
                        return;
                    }

                    const dateObject = new Date(vm.sendEmailTime);
                    const formattedDateTime = dateObject.toLocaleString('en-us', {
                        timeZone: 'Asia/Taipei',
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
                    vm.sendEmailState = "時間已設定";
                    break;
            }

            if (vm.selectedCoupon === "") {
                vm.selectedCoupon
                vm.sendEmailState = "沒有選擇折價券";
            }

            vm.couponMembers.forEach(element => {
                element.discountCode = vm.selectedCoupon;
            });


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
        },
        updateDeadline(item, value) {
            item.coupon.deadline = value;
        },
        publishCouponActivity(item) {
            axios({
                method: "GET",
                url: host_context + "shopDispatcher/publishCouponActivity",
                params: {
                    couponId: item.coupon.id
                }
            })
                .then(function (value) {
                    const result = value.data;
                    if (result.state === "ok") {
                        item.coupon.state = vm.enumCouponState.publish;
                    }


                    // if ("longTime" === value.data.state) {
                    //     console.log(value.data.msg);
                    //     setTimeout(function () {
                    //         getBackgroundMessage();
                    //     }, 1000);
                    // }
                    // else {
                    //     vm.sendEmailState = "傳送完成";
                    //     console.log("longTime process OK");
                    // }

                    // console.log("getBackgroundMessage then");
                })
                .catch(function (e) {
                    console.log("publishCouponActivity error " + e);
                });
        },
        numSelectChange(num) {
            vm.limitNum = num;
            getAllCouponActivity()
        },
        discountCodeSelectChange(value) {
            vm.selectedCoupon = value;
        },
        generateDiscountCode() {
            axios({
                method: "GET",
                url: host_context + "shopDispatcher/generateDiscountCode",
            })
                .then(function (value) {
                    const result = value.data;
                    if (result.state === "ok") {
                        vm.newCoupon.discountCode = result.content;
                    }

                    console.log("generateDiscountCode then");
                })
                .catch(function (e) {
                    console.log("generateDiscountCode error " + e);
                });
        },
        checkNumber(key, value) {
            let result = Number(value);

            let isShow = false;
            if (typeof result === 'number' && !isNaN(result)) {
                isShow = false;
            }
            else {
                isShow = true;
            }

            switch (key) {
                case 'discount':
                    vm.newCoupon.showError_discount = isShow;
                    break;
                case 'conditionPrice':
                    vm.newCoupon.showError_conditionPrice = isShow;
                    break;

            }

        },
        checkNumber_update(item, key, value) {
            let result = Number(value);

            let isShow = false;
            if (typeof result === 'number' && !isNaN(result)) {
                isShow = false;
            }
            else {
                isShow = true;
            }

            switch (key) {
                case 'discount':
                    item.showError_discount = isShow;
                    break;
                case 'conditionPrice':
                    item.showError_conditionPrice = isShow;
                    break;

            }
        }
    }
}).mount("#vue-body");

// vm.$data.message = 'SSSSS';
vm.getAllCouponActivity();


vm.nowDate = nowDate;
vm.minDate = vm.nowDate;

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
                vm.sendEmailState = "傳送郵件完成";
                console.log("longTime process OK");
            }

            console.log("getBackgroundMessage then");
        })
        .catch(function (e) {
            console.log("getBackgroundMessage error " + e);
        });
}

function getAllCouponActivity() {

    let sqlConditions = [];
    let conditions = [];

    sqlConditions.push({ "key": "limit", "value": vm.limitNum });
    sqlConditions.push({ "key": "offset", "value": 0 });

    let object = {
        "msg": "getAllCouponActivity",
        "conditions": conditions,
        "sqlConditions": sqlConditions
    }

    let jsonObject = JSON.stringify(object);
    let encodedJsonObject = encodeURIComponent(jsonObject);


    axios({
        method: "GET",
        // url: host_context + "shopDispatcher/getAllCouponActivity",
        url: host_context + "shopDispatcher/getCouponActivityByCondition",
        // url: "shopDispatcher/getCouponActivityByCondition",
        params: {
            params: encodedJsonObject
        }
    })
        .then(function (value) {
            vm.AllCouponActivityRequest = value.data;
            let state = value.data.state;
            if (state === "ok") {
                let couponActivity = value.data.content;

                for (let i = 0; i < couponActivity.length; i++) {
                    let deadline = couponActivity[i].coupon.deadline;
                    deadline = new Date(deadline);
                    deadline.setHours(deadline.getHours() + 8);
                    deadline = deadline.toISOString();
                    deadline = deadline.slice(0, 16);//16
                    couponActivity[i].coupon.deadline = deadline;
                }

                vm.showCouponActivity = true;
                vm.couponActivity = couponActivity;

                vm.couponActivity.forEach(element => {
                    element.coupon.showError_discount = false;
                    element.coupon.showError_conditionPrice = false;
                });


            }
            else {
                vm.showCouponActivity = false;
                console.log(value.data.content);

            }

            console.log("getAllCouponActivity then");
        })
        .catch(function (e) {
            console.log("getAllCouponActivity error " + e);
        });
}
