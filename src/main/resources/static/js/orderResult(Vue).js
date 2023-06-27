// 視窗變化時調整增添視窗內的文字
$(window).resize(function () {
    let windowSize = $(this).width();
    if (windowSize <= 830) {
        $('.addDetail table.byMyPick th:nth-child(3)').text('追蹤日期');
        $('.addDetail table.byMyPick th:last-child').text('加入購物');
        $('.addDetail table.byPurchaseLog th:nth-child(3)').text('購買日期');
        $('.addDetail table.byPurchaseLog th:last-child').text('加入購物');
        $('.addDetail table button').text('加入');
    } else {
        $('.addDetail table.byMyPick th:nth-child(3)').text('加入追蹤日期');
        $('.addDetail table.byMyPick th:last-child').text('加入本次購物');
        $('.addDetail table.byPurchaseLog th:nth-child(3)').text('近期購買日期');
        $('.addDetail table.byPurchaseLog th:last-child').text('加入購物');
        $('.addDetail table button').text('加入購物');
    }
});

const href = window.location.href;
const host = href.substring(0, href.indexOf('/', 8));
const projectHref = href.substring(0, href.lastIndexOf('Five_NBP.gg') + 11);
const projectFolder = '/Five_NBP.gg';

// 購物明細
const shoppingContent = Vue.createApp({
    data() {
        return {
            // 購物商品明細
            odProducts: [],
            // 消費折抵
            checkCoupon: '',
            nowBonus: '',
            usedBonus: '',
            // 配送方式
            deliver: '',
            address: {},
            // 付款方式
            payment: '',
            // 信用卡資訊
        }
    },
    methods: {
        leave: function (location, otherDetail) {
            // sessionStorage.clear();
            // sessionStorage.setItem('productId', otherDetail);
            window.location.replace(projectHref + '/' + location + '#' + otherDetail);
        }
    },
    computed: {
        productSubtotal: function () {
            let productSub = 0;
            let couponDiscount = 0;
            let bonus = 0;
            for (pic of this.odProducts) {
                productSub += pic.buyAmount * pic.price;
            }

            let finalPrice = productSub - couponDiscount - bonus;

            return finalPrice;
        },
        deliverCal() {
            return this.deliver === 'toCvs' ? 200 : 100;
        },
    },
    created() {
        this.odProducts = JSON.parse(sessionStorage.getItem('odProducts'));
        this.checkCoupon = JSON.parse(sessionStorage.getItem('checkCoupon'));
        this.usedBonus = Number.parseInt(sessionStorage.getItem('usedBonus'));
        this.nowBonus = Number.parseFloat(sessionStorage.getItem('nowBonus'));
        this.payment = sessionStorage.getItem('payment');
        this.deliver = sessionStorage.getItem('deliver');
        this.address = JSON.parse(sessionStorage.getItem('address'));
    }
}).mount('.shoppingContent');

// 推薦商品呈現
const promoProduct = Vue.createApp({
    data() {
        return {
            promoProduct: []
        }
    },
    created() {
        axios.get(projectFolder + '/Product?recomendFromAll=5')
            .then(res => this.promoProduct = res.data)
            .catch(err => console.log(err))
    }
}).mount('#promoProduct');

// 按下上一頁後無法返回 -- > Chrome瀏覽器限制無法順利觸發事件，待解決
window.addEventListener('popstate', () => {
    Swal.fire({
        title: '確定離開?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '是',
        cancelButtonText: '否'
    }).then(function (result) {
        if (result.isConfirmed) {

        }
    });
});