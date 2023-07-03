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

$('.helping').on('click', 'a#goCart', function (event) {
    event.preventDefault();
    if (loginStatus === null) {
        Swal.fire({
            title: '請先登入會員',
            showDenyButton: true,
            confirmButtonText: '登入',
            denyButtonText: `退出`,
            showClass: {
                popup: 'animate__animated animate__fadeInDown'
            },
            hideClass: {
                popup: 'animate__animated animate__fadeOutUp'
            }
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = '/gg4nbp/member_login.html';
            } else if (result.isDenied) {
            }
        });
    } else {
        window.location.href = '/gg4nbp/member/shoppingCart(Vue).html';
    }
});

// 二手收購確認是否會員已登入
$('.gobuylist').on('click', e => {
    e.preventDefault;
    fetch('/secondhand/addEvent', {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' }
    })
        .then(resp => {
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