import { host_context, nowDate } from './shopproductCommon.js';

const vm = Vue.createApp({
    data() {
        return {
            nowDate: '',
            minDate: '',
            newCouponActivity: {},
            newCoupon: {},
            newcouponActivity: {},
            couponActivity: [],
            coupon: [],
            AllCouponActivityRequest: {},
            showCouponActivity: true
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
    },
}).mount("#page-top");

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
