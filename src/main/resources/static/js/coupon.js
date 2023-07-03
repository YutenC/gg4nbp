import { host_context, nowDate } from './shopproductCommon.js';

let enumCouponState = { unpublish: 0, publish: 1, failed: 2 };
const vm = Vue.createApp({
    data() {
        return {

            product: {},
            couponActivity: null,
            coupon: []
        };
    },
    mounted() {
        // getAllCouponActivity();
    },
    methods: {
        getAllCouponActivity: function () {
            getAllCouponActivity();
            console.log('getAllCouponActivity');
        }

    }
}).mount("#vue-body");

vm.getAllCouponActivity();

function getAllCouponActivity() {
    // let sqlConditions = [];
    let conditions = [];

    conditions.push({ "key": "state", "value": enumCouponState.publish });
    // sqlConditions.push({ "key": "offset", "value": 0 });

    let object = {
        "msg": "getAllCouponActivity",
        "conditions": conditions
        // "sqlConditions": sqlConditions
    }

    let jsonObject = JSON.stringify(object);
    let encodedJsonObject = encodeURIComponent(jsonObject);

    axios({
        method: "GET",
        url: host_context + "shopDispatcher/getCouponActivityByCondition",
        params: {
            params: encodedJsonObject
        }
    })
        .then(function (value) {
            let state = value.data.state;
            if (state === "ok") {
                let couponActivity = value.data.content;

                couponActivity.forEach(element => {
                    let deadline = element.coupon.deadline;
                    deadline = new Date(deadline);
                    deadline = deadline.toLocaleString('zh-TW', { TimeZone: 'Asia/Taipei' });
                    element.coupon.deadline = deadline;
                });

                vm.couponActivity = couponActivity;
            }
            else {

                console.log(value.data.content);
            }

            console.log("getAllCouponActivity then");

        })
        .catch(function (e) {
            console.log("getAllCouponActivity error " + e);
        });
}