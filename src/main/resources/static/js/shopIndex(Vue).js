// cm輪播圖商品
let enumPageCurrentType = { NS: 2, PS: 22, XBOX: 12 };
const cm = Vue.createApp({
    data() {
        return {
            cmProduct: '',
            slideIndex: 1
        }
    },
    methods: {
        goProduct: function (location, otherDetail) {
            sessionStorage.setItem('currentShopProductDetail_id', otherDetail);
            window.location.href = projectFolder + '/' + location;
        },
        // Thumbnail image controls
        currentSlide: function (n) {
            this.showSlides(this.slideIndex = n);
        },
        plusSlides: function (n) {
            this.showSlides(this.slideIndex += n);
        },
        showSlides: function (n) {
            this.$nextTick(() => {
                let i;
                let slides = this.$refs.mySlides;
                let dots = this.$refs.dot;
                if (n > slides.length) { this.slideIndex = 1 }
                if (n < 1) { this.slideIndex = slides.length }
                for (i = 0; i < slides.length; i++) {
                    slides[i].style.display = "none";
                }
                for (i = 0; i < dots.length; i++) {
                    dots[i].className = dots[i].className.replace(" active", "");
                }
                slides[this.slideIndex - 1].style.display = "block";
                dots[this.slideIndex - 1].className += " active";
            });
        },
    },
    created() {
        let randomNum = Math.floor(Math.random() * 3);
        let productType = enumPageCurrentType[Object.keys(enumPageCurrentType)[randomNum]];
        const sort = { action: 'order', key: 'desc', value: 'buyTimes' };
        const conditions = [{ key: "type", value: productType }, { key: "state", value: 1 }, { key: "state", value: 12 }];
        const require = ['productIndexImage'];
        const sqlConditions = [{ key: 'limit', value: 5 }];
        const req = { sort: sort, required: require, sqlConditions: sqlConditions, conditions: conditions };
        axios({
            method: 'get',
            url: projectFolder + '/shopDispatcher/getAllProductByCondition',
            params: {
                params: encodeURIComponent(JSON.stringify(req))
            }
        }).then(res => this.cmProduct = res.data)
            .catch(err => console.log(err));
    },
    mounted() {
        this.$nextTick(() => {
            this.showSlides(this.slideIndex);
            setInterval(this.plusSlides, 5000, 1);
        });
    },
}).mount('#cm');

// 熱銷商品
const popularPicks = Vue.createApp({
    data() {
        return {
            popularPicks: []
        }
    },
    methods: {
        goProduct: function (location, otherDetail) {
            sessionStorage.setItem('currentShopProductDetail_id', otherDetail);
            window.location.href = projectFolder + '/' + location;
        }
    },
    created() {
        const sort = { action: 'order', key: 'desc', value: 'buyTimes' };
        const require = ['productIndexImage'];
        const sqlConditions = [{ key: 'limit', value: 5 }];
        const conditions = [{ key: "state", value: 1 }, { key: "state", value: 12 }];
        const req = { sort: sort, required: require, sqlConditions: sqlConditions, conditions: conditions };
        axios({
            method: 'get',
            url: projectFolder + '/shopDispatcher/getAllProductByCondition',
            params: {
                params: encodeURIComponent(JSON.stringify(req))
            }
        }).then(res => this.popularPicks = res.data)
            .catch(err => console.log(err));
    }
}).mount('#popularPicks');

// 一般新品
const newProdcut = Vue.createApp({
    data() {
        return {
            newProdcut: []
        }
    },
    methods: {
        goProduct: function (location, otherDetail) {
            sessionStorage.setItem('currentShopProductDetail_id', otherDetail);
            window.location.href = projectFolder + '/' + location;
        }
    },
    created() {
        const sort = { action: 'order', key: 'desc', value: 'launchTime' };
        const require = ['productIndexImage'];
        const sqlConditions = [{ key: 'limit', value: 5 }];
        const conditions = [{ key: "state", value: 1 }, { key: "state", value: 12 }];
        const req = { sort: sort, required: require, sqlConditions: sqlConditions, conditions: conditions };
        axios({
            method: 'get',
            url: projectFolder + '/shopDispatcher/getAllProductByCondition',
            params: {
                params: encodeURIComponent(JSON.stringify(req))
            }
        }).then(res => this.newProdcut = res.data)
            .catch(err => console.log(err));
    }
}).mount('#newProdcut');

// 二手商城商品
const secondSP = Vue.createApp({
    data() {
        return {
            secondhandproduct: [],
        }
    },
    methods: {
        goProduct: function (location, otherDetail) {
            sessionStorage.setItem('productId', otherDetail);
            window.location.href = projectFolder + '/' + location;
        }
    },
    created() {
        axios.get(projectFolder + '/secondhand/shp_main')
            .then(res => this.secondhandproduct = res.data)
            .catch(err => console.log(err))
    }
}).mount('#secondSP');