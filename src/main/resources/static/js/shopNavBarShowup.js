// 上方導覽列隱藏顯示動畫
window.addEventListener('scroll', () => {
    const scrollPositionY = window.pageYOffset;
    const mainNav = document.querySelector('.mainNav');
    if (scrollPositionY === 0) {
        mainNav.style.top = document.querySelector('nav.subNav').style.height;
    } else {
        mainNav.style.top = '0px';
        mainNav.style.transition = '300ms ease';
    }
});
