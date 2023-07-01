// 搜尋欄控制
$('.searchinput').on('keydown', function (e) {
    if (e.keyCode === 13 || e.keyCode === 108) {
        e.preventDefault();
        sessionStorage.setItem('searchKeyword', this.value);
        // window.location.href = projectFolder + '/shop/shopIndexSearchResult(Vue).html'
        window.location.href = '/src/main/resources/static/shop/shopIndexSearchResult(Vue).html'
    }
});

$('button.searchicon').on('click', function (e) {
    e.preventDefault();
    let docuWidth = $(document).width();
    if ($('.searchinput').val() === '') {
        if (docuWidth > 950) {
            $('.productCondition').toggle();
        }
        $('.searchinput').toggle();
        $('.searchinput').focus();
        $('.productline').toggleClass('onsearch');
    } else {
        sessionStorage.setItem('searchKeyword', $('.searchinput').val());
        // window.location.href = projectFolder + '/shop/shopIndexSearchResult(Vue).html'
        window.location.href = '/src/main/resources/static/shop/shopIndexSearchResult(Vue).html'
    }
});

