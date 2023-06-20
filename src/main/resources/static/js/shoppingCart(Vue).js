// 增添商品到購物車內按鈕變化

// '+' 號按鈕預設展開
$('.cartHeader div a.addItem img.icon.plus').addClass('on');

// 紀錄目前按下的按鈕
let clickTagAttr;
// 按鈕切換
$('.cartHeader div a.addItem').on('click', function (e) {
    e.preventDefault();
    // 按過的收合
    if (clickTagAttr !== undefined && clickTagAttr === $(this).attr('data-from')) {
        $(this).children('img').toggleClass('on');
        let switchTarget = $(this).attr('data-from');
        $(`.addDetail table.${switchTarget}`).toggleClass('on');
        // 非按過的展開並收起先前開過的
    } else {
        $('a.addItem img').removeClass('on');
        $('a.addItem img.icon.plus').addClass('on');
        $(this).children('img').toggleClass('on');
        let switchTarget = $(this).attr('data-from');
        $(`.addDetail table`).removeClass('on');
        $(`.addDetail table.${switchTarget}`).toggleClass('on');
    }
    clickTagAttr = $(this).attr('data-from');
});

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

// 從記錄添加商品到購物車
/*
- 顯示記錄商品
1. 取得商品資料
2. 顯示商品資料
3. 無庫存商品disable按鈕，並將按鈕文字改為無庫存
- 添加記錄商品到購物車
1. 取得該列的資料物件
2. 添加到購物車的table內
*/

// 從追蹤商品
const byMyPick = Vue.createApp({
    data() {
        return {
            mypick: '',
        }
    },
    methods: {
        addToCart: function (productId) {
            // 提取商品資訊
            axios.get('/Five_NBP.gg/Product?getOneProduct=' + productId)
                .then(res => {
                    let newProduct = res.data;
                    axios({
                        method: 'post',
                        url: '/Five_NBP.gg/ShoppingList',
                        params: {
                            demand: 'addOneShoppingList',
                            transObj: JSON.stringify(newProduct)
                        }
                    })
                        .then(res => console.log(res))
                        .catch(err => console.log(err))

                    // 前端調整畫面
                    for (let pd of shoppingContent.$data.shoppingList) {
                        if (pd.productId === newProduct.productId) {
                            pd.buyAmount += newProduct.buyAmount;
                            return;
                        }
                    }
                    newProduct.checked = true;
                    shoppingContent.$data.shoppingList.push(newProduct);

                    // 讓shoppingContent自行更新
                    // shoppingContent.renewList();
                }).catch(err => console.log(err));

            // 回存到購物清單
        }
    },
    created() {
        axios.get('/Five_NBP.gg/FollowList?getAll=true')
            .then(res => this.mypick = res.data)
            .catch(error => console.log(error))
    }
}).mount("table.byMyPick");

$('div.addDetail').on('click', 'button', function (e) {
    let productId = $(this).closest('tr').find('td.productId').text();
});

// 從購買紀錄
const byPurchaseLog = Vue.createApp({
    data() {
        return {
            byPurchaseLog: []
        }
    },
    methods: {
        addToCart: function (productId) {
            // 提取商品資訊
            axios.get('/Five_NBP.gg/Product?getOneProduct=' + productId)
                .then(res => {
                    let newProduct = res.data;
                    axios({
                        method: 'post',
                        url: '/Five_NBP.gg/ShoppingList',
                        params: {
                            demand: 'addOneShoppingList',
                            transObj: JSON.stringify(newProduct)
                        }
                    }).then(res => console.log(res))
                        .catch(err => console.log(err))

                    // 前端調整畫面
                    for (let pd of shoppingContent.$data.shoppingList) {
                        if (pd.productId === newProduct.productId) {
                            pd.buyAmount += newProduct.buyAmount;
                            return;
                        }
                    }
                    newProduct.checked = true;
                    shoppingContent.$data.shoppingList.push(newProduct);

                    // 讓shoppingContent自行更新
                    // shoppingContent.renewList();
                }).catch(err => console.log(err));
        }
    },
    created() {
        axios.get('/Five_NBP.gg/OrderDetail?getMemberAll=true')
            .then(res => {
                this.byPurchaseLog = res.data;
            })
            .catch(error => console.log(error))
    }
}).mount('table.byPurchaseLog');

// 購物車全選checkBox
$(document).ready(function () {
    // Set the "Check All" button as checked by default
    $('input#checkAll').prop('checked', true);
    // Child checkboxes event handler
    $('table#purchaseDetail tbody').on('change', 'input[type="checkbox"]', function () {
        var checkBoxNum = $('tbody input[type="checkbox"]').length;
        var checkedBoxNum = $('tbody input:checked').length;
        // Update "Check All" button based on the checked state of child checkboxes
        $('input#checkAll').prop('checked', checkedBoxNum === checkBoxNum);
    });
    // "Check All" button event handler
    $('input#checkAll').on('change', function () {
        var isChecked = $(this).prop('checked');
        // Set the checked state of child checkboxes based on the "Check All" button
        $('table#purchaseDetail input[type="checkbox"]').prop('checked', isChecked);
    });
});

const href = window.location.href;
const host = href.substring(0, href.indexOf('/', 8));
// 購物車商品
const shoppingContent = Vue.createApp({
    data() {
        return {
            // 購物商品明細
            shoppingList: [],
            shopTotal: 0,
            // 消費折抵
            discountRadio: 'coupon',
            couponCode: '',
            resCoupon: '',
            bonusStock: 10000,
            bonus: '',
            // 配送方式
            deliver: 'takuhai',
            address: { county: '桃園市', address: '' },
            countySelet: ['基隆市', '臺北市', '新北市', '桃園市', '新竹縣', '新竹市', '苗栗縣', '臺中市', '彰化縣',
                '南投縣', '雲林縣', '嘉義縣', '嘉義市', '臺南市', '高雄市', '屏東縣', '宜蘭縣', '花蓮縣', '臺東縣',
                '澎湖縣', '金門縣', '連江縣'],
            // 付款方式
            payment: 'credit',
            // 信用卡資訊
            card: {
                cardNum: '',
                cardValidMon: '',
                cardValidYr: '',
                cardValidNum: '',
            }
        }
    },
    methods: {
        // 移除商品
        removeItem: function (index) {
            let shoppingList = this.shoppingList;
            Swal.fire({
                title: '確定刪除?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: '是',
                cancelButtonText: '否'
            }).then(function (result) {
                if (result.isConfirmed) {
                    // 向後端發送刪除
                    console.log(shoppingList[index]);
                    axios.get('/Five_NBP.gg/ShoppingList?removeItem=' + shoppingList[index].productId)
                        .then(res => console.log(res))
                        .catch(err => console.log(err));
                    shoppingList.splice(index, 1);
                }
            });
            if (this.shoppingList.length === 0) {
                Swal.fire('購物車內已無商品，快去逛逛吧!');
                let newHref = host + '/Five_NBP.gg/shopIndex3.html';
                window.location.replace(newHref);
            }
        },
        // 消費折抵選擇
        checkCoupon: function () {
            // 向後端確認是否為有效折購代碼
            // 查詢到資料後更新this.resCoupon
            axios.get('/Five_NBP.gg/Coupon?couponCode=' + this.couponCode)
                .then(res => {
                    if (res.data != '') {
                        this.resCoupon = res.data;
                        this.couponCondition();
                    } else {
                        this.resCoupon = '';
                        Swal.fire('折價券號碼錯誤')
                    }
                })
                .catch(err => console.log(err))
        },
        // (從後端)更新購物明細
        renewList: function () {
            axios.get('/Five_NBP.gg/ShoppingList?getAll=true')
                .then(res => this.shoppingList = res.data)
                .catch(err => console.log(err))
        },
        // 判斷優惠碼是否達消費門檻
        couponCondition: function () {
            if (this.resCoupon === '') {
                return;
            }
            if (this.shopTotal < this.resCoupon.conditionPrice) {
                this.resCoupon = '';
                Swal.fire('購買金額未達門檻，不可使用');
            }
        },
        fixToNum: function (event) {
            let str = event.target.value;
            // 用正則表達式，將找到的全部(g)非數字值，取代為空字串
            str = str.replace(/\D/g, "");
            event.target.value = str;
        },
        bonusValid: function (event) {
            let str = event.target.value;
            // 用正則表達式，將找到的全部(g)非數字值，取代為空字串
            str = str.replace(/\D/g, "");
            if (Number.parseInt(str) > this.bonusStock) {
                str = this.bonusStock;
            }
            event.target.value = str;
        },
        // 結帳：將前端的結帳相關資料送到後端
        checkOut: function (ecpay) {
            if (this.address.address === '' ||
                ((this.payment === 'credit' && ecpay === false) &&
                    (this.card.cardNum.length < 16 || this.card.cardValidMon.length < 2 ||
                        this.card.cardValidYr.length < 4 || this.card.cardValidNum.length < 3))) {
                let alertMsg = '以下資料不完整：';
                this.address.address === '' ? alertMsg += '\n配送地址' : '';
                this.card.cardNum.length < 16 ? alertMsg += '\n信用卡號' : '';
                this.card.cardValidMon.length < 2 ? alertMsg += '\n信用卡有效月份' : '';
                this.card.cardValidYr.length < 4 ? alertMsg += '\n信用卡有效年份' : '';
                this.card.cardValidNum.length < 3 ? alertMsg += '\n信用卡驗證碼' : '';
                Swal.fire(alertMsg);
                return;
            }
            axios({
                method: 'post',
                url: '/Five_NBP.gg/OrderMaster',
                params: {
                    demand: 'checkOut',
                    toEcpay: ecpay,
                    discountRadio: this.discountRadio,
                    coupon: JSON.stringify(this.resCoupon),
                    bonus: this.bonus,
                    deliver: this.deliver,
                    payment: this.payment,
                },
                data: {
                    transObj: [...this.shoppingList].filter(item => item.checked === true),
                    card: this.card,
                    address: this.address
                }
            }).then(res => {
                let resJson = res.data;
                let odProducts = resJson.odProducts;
                let checkCoupon = resJson.checkCoupon;
                let usedBonus = resJson.usedBonus;
                let nowBonus = resJson.nowBonus;
                sessionStorage.setItem("odProducts", JSON.stringify(odProducts));
                if (checkCoupon !== undefined) {
                    sessionStorage.setItem("checkCoupon", JSON.stringify(checkCoupon));
                }
                sessionStorage.setItem("usedBonus", usedBonus);
                sessionStorage.setItem("nowBonus", nowBonus);
                sessionStorage.setItem("payment", this.payment);
                sessionStorage.setItem("deliver", this.deliver);
                sessionStorage.setItem("address", JSON.stringify(this.address));
                window.location.replace(host + '/Five_NBP.gg/')
            }).catch(err => console.log(err))
        },
    },
    computed: {
        productSubtotal: function () {
            let productSub = 0;
            let couponDiscount = 0;
            let bonus = 0;
            for (pic of this.shoppingList) {
                if (pic.checked == true) {
                    productSub += pic.buyAmount * pic.price;
                }
            }
            this.shopTotal = productSub;

            this.couponCondition();

            if (this.discountRadio === 'coupon') {
                couponDiscount = this.resCoupon === '' ? 0 : Number.parseInt(this.resCoupon.discount);
            } else {
                bonus = Number.parseInt(this.bonus);
            }

            let finalPrice = isNaN(productSub - couponDiscount - bonus) ?
                productSub - couponDiscount : productSub - couponDiscount - bonus;

            if (finalPrice < 0) {
                if (this.discountRadio === 'bonus' && productSub < bonus) {
                    this.bonus = productSub;
                }
                finalPrice = 0;
            }

            return finalPrice;
        },
        deliverCal() {
            return this.deliver === 'toCvs' ? 200 : 100;
        },
    },
    created() {
        this.renewList();
    }
}).mount('.shoppingContent');

// 推薦商品呈現
const promoProduct = Vue.createApp({
    data() {
        return {
            promoProduct: [
                {
                    id: 1111, productName: 'abcdefg', link: 'dddddd', productImg: '../img/peripherals/Nintendo/Zelda/2a14aa702d831e8f7f2803e1601l4t05.jpg',
                    price: 2000
                },
                {
                    id: 1111, productName: 'abcdefg', link: 'dddddd', productImg: '../img/peripherals/Nintendo/Zelda/2a14aa702d831e8f7f2803e1601l4t05.jpg',
                    price: 2000
                },
                {
                    id: 1111, productName: 'abcdefg', link: 'dddddd', productImg: '../img/peripherals/Nintendo/Zelda/2a14aa702d831e8f7f2803e1601l4t05.jpg',
                    price: 2000
                }
            ]
        }
    }
}).mount('#promoProduct');