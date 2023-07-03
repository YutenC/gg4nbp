const projectFolder = '/gg4nbp';

const productResults = Vue.createApp({
    data() {
        return {
            productResults: []
        }
    },
    methods: {
        leave: function (location, otherDetail, event) {
            event.preventDefault();
            sessionStorage.setItem('currentShopProductDetail_id', otherDetail);
            window.location.href = projectFolder + '/' + location;
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
        leave: function (location, otherDetail, event) {
            event.preventDefault();
            sessionStorage.setItem('prodcutId', otherDetail);
            window.location.href = projectFolder + '/' + location;
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