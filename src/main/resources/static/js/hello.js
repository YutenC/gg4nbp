import { host_context, nowDate } from './shopproductCommon.js';


const vm = Vue.createApp({
    data() {
        return {
            locathost: '',
            contextpath: '',
            host_context: '',
            nowDate: '',
            minDate: '',
            arr: ['008', 'HG', 'yy'],
            newCoupon: {},
            coupons: [],
        };
    },
    methods: {
        getAllCoupon: function () {
            console.log('getproduct');
            axios({
                method: "GET",
                url: vm.host_context + "helloJsonServlet_",
                // params: {
                //     pro_id: 2
                // }
            })
                .then(function (value) {
                    vm.coupons = value.data;
                    console.log("getAllCoupon then");
                })
                .catch(function (e) {
                    console.log("getAllCoupon error " + e);
                });
        },
        deleteCoupon: function (id) {
            console.log("deleteCouponActivity: id " + id);

            axios({
                method: "GET",
                url: vm.host_context + "helloDispatcher/deleteCoupon",
                // url: "http://localhost:8080/Five_NBP_gg_war/helloDispatcher/deleteCoupon",
                params: {
                    coupon_id: id
                }
            })
                .then(function (value) {
                    console.log("deleteCouponActivity then ");
                    vm.getAllCoupon();
                })
                .catch(function (e) {
                    console.log("deleteCouponActivity error " + e);
                });

        },
        addCoupon: function () {
            console.log("addCoupon: id ");
            let json_newCoupon = JSON.stringify(vm.newCoupon);

            axios({
                method: "POST",
                url: vm.host_context + "helloDispatcher/addCoupon",
                params: {
                    json_newCoupon: json_newCoupon,
                    // newCoupon:vm.newCoupon
                }
            })
                .then(function (value) {
                    console.log("addCoupon then ");
                    vm.getAllCoupon();
                })
                .catch(function (e) {
                    console.log("addCoupon error " + e);
                });

        },
        // gethost_context: function () {
        //     let pathName = window.document.location.pathname;
        //     let locathost = window.document.location.origin;
        //     pathName = '/';
        //     if ('/' === pathName) {
        //         vm.host_context = locathost + "/";
        //     }
        //     else {
        //         let contextpath = pathName.split('/')[1];
        //         vm.host_context = locathost + "/" + contextpath + "/";
        //     }
        // }

    },
}).mount("#page-top");

// vm.gethost_context();

vm.host_context = host_context;
console.log("host_context: " + host_context);


const currentDate = new Date();
const year = currentDate.getFullYear();
const month = String(currentDate.getMonth() + 1).padStart(2, '0');
const day = String(currentDate.getDate()).padStart(2, '0');

vm.nowDate = `${year}-${month}-${day}`;
vm.minDate = vm.nowDate;
vm.newCoupon.deadline = vm.nowDate;

vm.getAllCoupon();



