
/* ------ minimenu控制區 ------- */

// 網頁下滑超過150px顯示控制區
$(window).on('scroll', () => {
    if (document.body.scrollTop > 400 || document.documentElement.scrollTop > 400) {
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
