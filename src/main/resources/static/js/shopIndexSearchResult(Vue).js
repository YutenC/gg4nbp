const projectFolder = 'http://localhost:8080/gg4nbp';

const productResults = Vue.createApp({
    data() {
        return {
            productResults: []
        }
    },
    methods: {
        leave: function (location, otherDetail) {
            sessionStorage.setItem('productId', otherDetail);
            window.location.href = projectHref + '/' + location;
        }
    },
    created() {
        let searchKeyword = sessionStorage.getItem('searchKeyword');
        axios.get(projectFolder + '/shopDispatcher/searchProducts?search=' + searchKeyword)
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
            sessionStorage.setItem('productId', otherDetail);
            window.location.href = projectHref + '/' + location;
        }
    },
    created() {
        let searchKey = sessionStorage.getItem('searchKeyword');
        axios({
            method: 'post',
            url: projectFolder + '/secondhand/shp_keywordSearch',
            data: { name: searchKey }
        }).then(res => this.secondProductResults = res.data)
            .catch(err => console.log(err))
    }
}).mount('#secondProductResults');