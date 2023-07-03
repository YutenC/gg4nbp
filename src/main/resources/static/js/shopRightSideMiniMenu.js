
/* ------ minimenu控制區 ------- */

// 網頁下滑超過150px顯示控制區
$(window).on('scroll', () => {
    if (document.body.scrollTop > 500 || document.documentElement.scrollTop > 500) {
        $('main .miniMenu').css('display', 'block');
    } else {
        $('main .miniMenu').css('display', 'none');
    }
});

// 返回頂端按鈕控制
$('.miniMenuBut .goTop').on('click', (e) => {
    e.preventDefault();
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
});

// 開關瀏覽紀錄
$('div.miniMenuBut a.viewRecord').on('mouseover click', (e) => {
    e.preventDefault();
    $('div.miniMenu div.viewRecord').fadeToggle(200);
});

// 移開瀏覽紀錄按鈕檢視區消失
let miniButs = document.querySelectorAll('div.miniMenuBut a');
for (let but of miniButs) {
    if (but.className !== 'viewRecord') {
        but.addEventListener('mouseover', () => {
            document.querySelector('div.miniMenu div.viewRecord').style = 'display: none';
        });
    }
}

// 移開瀏覽檢視區檢視區消失
$('div.miniMenuContent div.viewRecord').on('mouseleave', (e) => {
    $('div.miniMenu div.viewRecord').css('display', 'none');
});

// 購物車按鈕被點選時檢核登入並轉導
$('div.miniMenuBut').on('click', 'a.shoppingCart', (event) => {
    event.preventDefault();
    const loginStatus = sessionStorage.getItem('id');
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
