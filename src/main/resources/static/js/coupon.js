import { host_context, nowDate } from './shopproductCommon.js';

const vm = Vue.createApp({
    data() {
        return {
            product: {},
            couponActivity: null,
            coupon: []
        };
    },
    mounted() {
        getAllCouponActivity();
    },
    methods: {
        getAllCouponActivity: function () {
            console.log('getAllCouponActivity');

        }

    }
}).mount(".shopmain");

// vm.getAllCouponActivity();

function getAllCouponActivity() {
    axios({
        method: "GET",
        // url: "http://localhost:8080/shop/shopDispatcher/getAllCouponActivity",
        url: host_context + "shopDispatcher/getAllCouponActivity",
        params: {
            activityCode: 2
        }
    })
        .then(function (value) {
            vm.couponActivity = value.data.content;

            // for (let i = 0; i < vm.couponActivity.length; i++) {
            //     console.log(vm.couponActivity[i].coupon);
            //     let json = JSON.parse(vm.couponActivity[i].coupon);
            //     vm.coupon.push(json);
            //     vm.couponActivity[i].coupon = json;
            // }

            console.log("getAllCouponActivity then");

        })
        .catch(function (e) {
            console.log("getAllCouponActivity error " + e);
        });
}