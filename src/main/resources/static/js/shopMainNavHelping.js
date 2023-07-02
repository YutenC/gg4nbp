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