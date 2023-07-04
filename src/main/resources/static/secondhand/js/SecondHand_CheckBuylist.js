insertSide();
changeTitle('二手回收申請查詢');



document.addEventListener("DOMContentLoaded", e => {
    $('#end_input').val(getNow());
    $('#start_input').val(getNow(0));
})

$('#end_btn').on('click', e => {
    $('#end_input').val(getNow());
})

$('#start_btn').on('click', e => {
    $('#start_input').val(getNow(0));
})



/**
 * 
 * @param {int} i 預設為 1 , 輸入 0 則回傳一小時前
 * @returns 回傳 YYYY-MM-DD
 */
function getNow(i = 1) {
    const date = new Date();
    const year = date.getFullYear();
    let month = date.getMonth() + i + "";
    month = month.length == 2 ? "-" + month : "-0" + month;
    let day = date.getDate() + "";
    day = day.length == 2 ? "-" + day : "-0" + day;
    return year + month + day;
}
