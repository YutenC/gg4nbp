const projectFolder = '/Five_NBP.gg';

const productResults = Vue.createApp({
    data() {
        return {
            productResults: []
        }
    },
    methods: {
        leave: function (location, otherDetail) {
            sessionStorage.clear();
            sessionStorage.setItem('productId', otherDetail);
            window.location.replace(projectHref + '/' + location);
        }
    },
    created() {
        axios.get(projectFolder + '')
            .then(res => this.productResults = res.data)
            .catch(err => console.log(err))
    }
}).mount('#productResults');

const secondProductResults = Vue.createApp({
    data() {
        return {
            secondProductResults: []
        }
    },
    methods: {
        leave: function (location, otherDetail) {
            sessionStorage.clear();
            sessionStorage.setItem('productId', otherDetail);
            window.location.replace(projectHref + '/' + location);
        }
    },
    created() {
        axios.get(projectFolder + '')
            .then(res => this.secondProductResults = res.data)
            .catch(err => console.log(err))
    }
}).mount('#secondProductResults');