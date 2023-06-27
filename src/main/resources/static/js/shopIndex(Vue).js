const projectFolder = '/gg4nbp';

$('.searchinput').on('keydown', function (event) {
    sessionStorage.setItem('searchKeyword', event.target.value);
    window.location.href = projectFolder + '/shopIndexSearchResult(Vue).html'
});