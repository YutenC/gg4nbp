const detailUnit = Vue.createApp({
    data() {
        return {
            mypick: []
        }
    },
    methods: {
        remove(index, id) {
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
                    detailUnit.mypick.splice(index, 1);
                    axios.get('/Five_NBP.gg/FollowList?delPdId=' + id)
                        .then(res => console.log(res.data))
                        .catch(err => console.log(err))
                }
            });
        }
    },
    created() {
        axios.get('/Five_NBP.gg/FollowList?getAll=true')
            .then(res => this.mypick = res.data)
            .catch(err => console.log(err));
    }
}).mount('.detailUnit');