const projectFolder = 'http://localhost:8080/gg4nbp';

const secondSP = Vue.createApp({
    data() {
        return {
            secondhandproduct: [],
        }
    },
    methods: {
        leave: function (location, otherDetail) {
            sessionStorage.setItem('productId', otherDetail);
            window.location.href = projectHref + '/' + location;
        }
    },
    created() {
        axios.get(projectFolder + '/shop/shpView')
            .then(res => this.secondhandproduct = res.data)
            .catch(err => console.log(err))
    }
}).mount('#secondSP');