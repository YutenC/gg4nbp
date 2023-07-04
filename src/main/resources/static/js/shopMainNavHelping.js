// 開關功能區
$('div.helping a.funcSelect').on('click', (e) => {
    e.preventDefault();
    $('div.centerFunc').toggleClass('on');
    $('a.funcSelect img').toggleClass('on');
});

// 確認登入狀態顯示不同的功能區
const loginStatus = sessionStorage.getItem('id');
if (loginStatus === null) {
    $('#memberCenter').remove();
} else {
    $('#login').remove();
}

// 點選購物車時進行檢核
$('.helping').on('click', 'a#goCart', function (event) {
    event.preventDefault();
    axios.post(projectFolder + '/ShoppingList')
        .then(res => {
            if (!res.data.successful) {
                let timerInterval
                Swal.fire({
                    title: '您尚未登入！',
                    html: ' <b></b> 秒後跳轉到登入頁面',
                    timer: 3000,
                    timerProgressBar: false,
                    didOpen: () => {
                        Swal.showLoading()
                        const b = Swal.getHtmlContainer().querySelector('b')
                        timerInterval = setInterval(() => {
                            b.textContent = Math.floor(Swal.getTimerLeft() / 1000)
                        }, 100)
                    },
                    willClose: () => {
                        clearInterval(timerInterval)
                    }
                }).then((result) => {
                    if (result.dismiss === Swal.DismissReason.timer) {
                        sessionStorage.setItem('backTo', window.location.href);
                        window.location.href = projectFolder + '/member_login.html';
                    }
                });
            } else {
                window.location.href = projectFolder + '/member/shoppingCart(Vue).html';
            }
        }).catch(err => console.log(err));
});

// 二手收購確認是否會員已登入
$('.gobuylist').on('click', e => {
    e.preventDefault();
    fetch(projectFolder + '/secondhand/addEvent', {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' }
    })
        .then(resp => {
            console.log(resp);
            if (resp.redirected == true) {
                let timerInterval
                Swal.fire({
                    title: '您尚未登入！',
                    html: ' <b></b> 秒後跳轉到登入頁面',
                    timer: 3000,
                    timerProgressBar: false,
                    didOpen: () => {
                        Swal.showLoading()
                        const b = Swal.getHtmlContainer().querySelector('b')
                        timerInterval = setInterval(() => {
                            b.textContent = Math.floor(Swal.getTimerLeft() / 1000)
                        }, 100)
                    },
                    willClose: () => {
                        clearInterval(timerInterval)
                    }
                }).then((result) => {
                    if (result.dismiss === Swal.DismissReason.timer) {
                        sessionStorage.setItem('backTo', location.href);
                        location.href = resp.url;
                    }
                })
            } else {
                location.href = projectFolder + '/secondhand/SecondHand_Buylist.html';
            }
        })
});