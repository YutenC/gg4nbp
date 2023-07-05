
const omManage = Vue.createApp({
    data() {
        return {
            omList: [],
            nowPage: 1,  // 與offset間的轉換： nowPage = offset + 1
            totalListItem: 0,
            sortBy: 0,
            sortWay: 0,
            criteria: '1',
            searchKeyword: ''
        }
    },
    methods: {
        changeSort: function (sortBy, sortWay) {
            this.sortBy = sortBy;
            this.sortWay = sortWay;
            this.nowPage = 1;
            if (this.searchKeyword !== '') {
                this.searchUser();
            } else if (this.criteria === 1) {
                this.renewList(this.nowPage);
            } else {
                this.renewListWithSelect();
            }
        },
        watchDetail: function (omid) {
            sessionStorage.setItem('orderId', omid);
            sessionStorage.setItem('offset', this.nowPage - 1);
            window.location.href = projectFolder + '/manager/orderManageDetail.html';
        },
        renewList: function (pageNum) {
            this.nowPage = pageNum;
            let offset = pageNum - 1;
            let sortBy = this.sortBy;
            let sortWay = this.sortWay;
            sessionStorage.setItem('offset', offset);
            if (this.searchKeyword !== '') {
                this.renewSearchResults();
            } else if (this.criteria === '1') {
                axios({
                    method: 'get',
                    url: projectFolder + '/OrderMaster',
                    params: {
                        'manageAll': offset,
                        'sortBy': sortBy,
                        'sortWay': sortWay
                    },
                })
                    .then(res => this.omList = res.data)
                    .catch(err => console.log(err));
            } else {
                this.renewListWithSelect();
            }
            this.getListLength();
        },
        getListLength: function () {
            axios({
                method: 'get',
                url: projectFolder + '/OrderMaster',
                params: {
                    countListLength: 'manager',
                    criteria: this.criteria,
                }
            }).then(res => this.totalListItem = res.data)
                .catch(err => console.log(err));
        },
        selectionChange() {
            this.nowPage = 1;
            this.renewListWithSelect();
        },
        renewListWithSelect: function () {
            this.searchKeyword = '';
            if (this.criteria === '1') {
                this.renewList(this.nowPage);
            } else {
                axios({
                    method: 'get',
                    url: projectFolder + '/OrderMaster',
                    params: {
                        manageCondition: 'y',
                        offset: this.nowPage - 1,
                        selection: this.criteria,
                        sortWay: this.sortWay
                    }
                }).then(res => this.omList = res.data)
                    .catch(err => console.log(err));
            }
            this.getListLength();
        },
        getLastest: function () {
            let criteria = this.criteria;
            let pageNum = this.nowPage;
            axios.get(projectFolder + '/OrderMaster?fresh=y')
                .then(res => this.renewList(pageNum))
                .catch(err => console.log(err));
        },
        searchUser: function (event) {
            if (this.searchKeyword === '') {
                this.searchKeyword = event.target.value;
            }
            this.nowPage = 1;
            this.renewSearchResults();
        },
        renewSearchResults: function () {
            axios({
                method: 'get',
                url: projectFolder + '/OrderMaster',
                params: {
                    searchUser: this.searchKeyword,
                    offset: this.nowPage - 1,
                    sortWay: this.sortWay
                }
            }).then(res => this.omList = res.data)
                .catch(err => console.log(err));
            this.getSearchLength();
        },
        getSearchLength: function () {
            axios({
                method: 'get',
                url: projectFolder + '/OrderMaster',
                params: {
                    searchLength: 'y',
                    keyword: this.searchKeyword,
                }
            }).then(res => this.totalListItem = res.data)
                .catch(err => console.log(err))
        }
    },
    created() {
        let offset = sessionStorage.getItem('offset');
        if (offset === null) {
            offset = 0;
        } else {
            offset = Number.parseInt(offset);
        }

        this.nowPage = offset + 1;

        axios({
            method: 'get',
            url: projectFolder + '/OrderMaster',
            params: {
                'manageAll': offset,
                'sortBy': this.sortBy,
                'sortWay': this.sortWay
            },
        })
            .then(res => this.omList = res.data)
            .catch(err => console.log(err));

        axios.get(projectFolder + '/OrderMaster?countListLength=manager')
            .then(res => this.totalListItem = res.data)
            .catch(err => console.log(err));

        sessionStorage.removeItem('offset');
    }
}).mount('#omManage');

// 測試能否解決快取造成的頁面資訊顯示錯誤
window.onpageshow = function (event) {
    if (event.persisted) {
        window.location.reload()
    }
};