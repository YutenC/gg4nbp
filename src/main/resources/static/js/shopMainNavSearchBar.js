// 搜尋欄控制
$('.searchicon').on('click', (e) => {
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
        $('.search').submit();
    }
});


