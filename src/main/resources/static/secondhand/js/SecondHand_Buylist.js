let reqaa = null;
let id = null ;
let del = [];

changeTitle('二手回收申請');
$('.div_search').remove();


$('div.inputBlock>div>input').on('focus', function (e) {
    $(this).next().stop().animate({ top: "-8px", fontSize: "10px", color: "blue" }, 100, "linear", function (e) {
        $(this).css('color', 'blue');
    })
})


$('div.inputBlock>div>input').on('blur', function (e) {
    if ($(this).val()) {
        $(this).next().css('color', 'gray');
        return;
    }
    $(this).next().stop().css('color', 'gray').animate({ top: "5px", fontSize: "16px" }, 200, "linear");
})


$('#getshot').on('click', function (e) {
    $(this).next().click();
})


$('#getshot').next().on('change', function (e) {
    del = [];
    $('#imgView>div').remove();

    let uploadImg = e.target.files || e.dataTransfer.files;

    if (!uploadImg.length) {
        return;
    }

    for (let i = 0; i < uploadImg.length; i++) {


        const div = document.createElement('div');
        const img = document.createElement('img');
        const btn = document.createElement('button');
        $(btn).attr('class', 'del_btn').text('✖');
        const reader = new FileReader();
        if (uploadImg[i].size >= 5242880) {
            $(img).addClass('-warning');
        }
        $('#imgView').append(div);
        div.append(img);
        div.append(btn);

        reader.readAsDataURL(uploadImg[i]);
        reader.addEventListener('load', e => {
            img.src = e.target.result;
        });


        btn.addEventListener('click', e => {
            del.push(i);
            div.remove();
        })

        img.addEventListener('click',()=>{
            Swal.fire({
                html: `<img id="fullImage" src="${img.src}">`,
                background: 'rgba(255, 255, 255, 0)',
                padding: 0,
                width: 900,
                boxShadow : 'none',
                showConfirmButton: false,
            })
        })

    }



})


$('#commit').on('click', function (e) {
    let type = $('#type_1').val() + $('#type_2').val();
    let s_estimate = estimate.value || 0;
    let img_list = [];
    const file = $('#getshot').next()[0].files;
    id =  sessionStorage.getItem('EventId') ?? null;


if($('img').hasClass('-warning')){
    Swal.fire({
        icon: 'error',
        title: '圖片過大(超過5MB)'
    })
    return;
}



    filter:
    for (let i = 0; i < file.length; i++) {
        const formData = new FormData();
        for (let j = 0; j < del.length; j++) {
            if (i === del[j]) {
                continue filter;
            }
        }
        formData.append(id+"/"+file[i].name, file[i]);
        img_list.push({ image: file[i].name });
        const xhr = new XMLHttpRequest();
        xhr.open('POST', 'upFile', true);
        xhr.send(formData);
    }
    del = [];

    fetch('../member/update4Member', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            eventId: sessionStorage.getItem('EventId'),
            productName: productName.value,
            content: content.value,
            type: type,
            estimate: s_estimate,
            applicantBankNumber: applicantBankNumber.value,
            image : img_list,
        })
    })
        .then(resp => {
            if (resp.redirected == true) {
                let timerInterval
                Swal.fire({
                    title: '您尚未登入！',
                    html: ' <b></b> 秒後跳轉到登入頁面',
                    timer: 3000,
                    timerProgressBar: false,
                    didOpen: () => {
                        Swal.showLoading()
                        const b = Swal.getHtmlContainer().querySelector('b')
                        timerInterval = setInterval(() => {
                            b.textContent = Math.floor(Swal.getTimerLeft()/1000)
                        }, 100)
                    },
                    willClose: () => {
                        clearInterval(timerInterval)
                    }
                }).then((result) => {
                    if (result.dismiss === Swal.DismissReason.timer) {
                        location.href = resp.url;
                    }
                })
            } else {
                return resp.json();
            }
        })
        .then(obj => {
            reqaa = obj ;
            checkpage(obj[0]);
            sessionStorage.removeItem('EventId');
        })





})







function checkpage(obj) {
    reqaa = obj;
    if (obj?.eventId) {
        $('.inputBlock').remove();
        const div = document.createElement('div');
        div.innerHTML += `
        <div class="return_title">申請結果</div>
        <hr>
    <div><div>訂單編號 : </div><span>${obj.eventId}</span></div>
    <div><div>會員名稱 : </div><span>${obj.memberName}</span></div>
    <div><div>商品名稱 : </div><span>${obj.productName}</span></div>
    <div><div>種類 : </div><span>${obj.type}</span></div>
    <div><div>內容 : </div><span>${obj.content}</span></div>
    <div><div>銀行帳號 : </div><span>${obj.applicantBankNumber}</span></div>
    <div><div>預估價格 : </div><span>${obj.estimate}</span></div>
    <div><div>是否成功 : </div><span>${obj.message}</span></div>
    <div><div>審核狀態 : </div><span>${obj.approvalState}</span></div>
    <hr>
    <div class="btnArea">
    <button onclick="gohome(event || window.event)">回首頁</button>
    </div>
    `;
        div.setAttribute('class', 'return_box');
        $('.showArea').append(div);
        $('.content').attr('style', 'width : 40%');
    } else {
        $('.inputNotice')[0].innerHTML = '* 輸入錯誤'

    }
    document.documentElement.scrollTop = 0;

}



$('#cancel').on('click', e => {
    e.preventDefault;
    location.href = 'SecondHand_MainView.html';
})

function goback() {
    history.go(0);
}


function gohome(e) {
    e.preventDefault;
    location.href = "SecondHand_MainView.html";
    

}



function testBlock(){
    let type = $('#type_1').val() + $('#type_2').val();
    let s_estimate = estimate.value || 0;
    let img_list = [];
    const file = $('#getshot').next()[0].files;

    filter:
    for (let i = 0; i < file.length; i++) {
        const formData = new FormData();
        for (let j = 0; j < del.length; j++) {
            if (i === del[j]) {
                continue filter;
            }
        }
        formData.append(file[i].name, file[i]);
        img_list.push({ image: file[i].name });
        const xhr = new XMLHttpRequest();
        xhr.open('POST', 'upFile', true);
        xhr.send(formData);
    }
    del = [];

    fetch('addEvent', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            productName: productName.value,
            type: type,
            content: content.value,
            estimate: s_estimate,
            applicantBankNumber: applicantBankNumber.value,
            image: img_list
        })
    }).then(resp => resp.json())
        .then(obj => {
            Swal.fire({
                icon: 'error',
                text: obj.str ?? obj.message,
              })
        })

}


document.addEventListener('DOMContentLoaded',function(){
id =  sessionStorage.getItem('EventId') ?? null;
if (id) 
    return;

    fetch('addEvent', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            productName: "productName",
            type: "00",
            content: "content",
            applicantBankNumber: "0",
        })
    })
        .then(resp => resp.json())
        .then(obj => {
            reqaa = obj ;
            sessionStorage.setItem('EventId',obj.eventId);
        })

})

