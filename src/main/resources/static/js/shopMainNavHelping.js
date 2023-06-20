$('div.helping a.funcSelect').on('click', (e) => {
    e.preventDefault();
    $('div.centerFunc').toggleClass('on');
    $('a.funcSelect img').toggleClass('on');
});