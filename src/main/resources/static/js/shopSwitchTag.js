// 商品詳情分頁切換
$('div.tagPage').on('click', 'ul.contentTab a.tab', function (e) {
    e.preventDefault();
    $('a.tab').removeClass('-on');
    $('div.tab').removeClass('-on');
    $(this).addClass('-on');
    let targetTab = $(this).attr('data-target');
    $('div.tab').each(function (index, item) {
        if ($(item).hasClass(targetTab)) {
            $(item).addClass('-on');
        }
    });
});