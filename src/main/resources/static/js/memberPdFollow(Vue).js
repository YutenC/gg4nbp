const detailUnit = Vue.createApp({
    data() {
        return {
            mypick: []
        }
    },
    methods: {
        goProduct: function (location, otherDetail) {
            sessionStorage.setItem('currentShopProductDetail_id', otherDetail);
            window.location.replace(projectFolder + '/' + location);
        },
        addCart: function (action, id) {
            let transObj = { productId: id, buyAmount: 1 };
            axios({
                method: "Post",
                url: projectFolder + '/' + "ShoppingList",
                // withCredentials: true,
                // crossDomain: true,
                params: {
                    demand: "addOneShoppingList",
                    transObj: JSON.stringify(transObj),
                }
            })
                .then(function (response) {
                    let result = response.data;
                    if (result != null && result.redirect) {
                        window.location.href = projectFolder + "/member_login.html";
                    }
                    else {
                        if (action == 1) {
                            window.location.href = "../member/shoppingCart(Vue).html";
                        }
                    }

                    Toast.fire({
                        icon: 'success',
                        title: '已加入購物車'
                    });
                })
                .catch(function (e) {
                    console.log("addCart error " + e);
                });
        },
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
                    axios({
                        method: "Get",
                        url: projectFolder + "shopDispatcher/addFollow",
                        params: {
                            id: id,
                            redirectUrl: "http://localhost:8080/gg4nbp/shop/shopProductDetail.html"
                        }
                    })
                        .then(function (value) {
                            let result = value.data;

                            if (result.state.toLowerCase() === "redirect") {
                                console.log(result.msg);
                                window.location.href = result.msg;
                            }
                            else if (result.state.toLowerCase() === "ok") {
                                this.mypick[index].follow = result.content;
                            }

                            if (this.mypick[index].follow === 0) {
                                Toast.fire({
                                    icon: 'error',
                                    title: '已從追蹤清單移除'
                                })
                            }
                        })
                        .catch(function (e) {
                            console.log("addFollow error " + e);
                        });
                }
            });
        }
    },
    created() {
        axios.get(projectFolder + '/shopDispatcher/getFollowByMember')
            .then(res => this.mypick = res.data)
            .catch(error => console.log(error))
    }
}).mount('.detailUnit');