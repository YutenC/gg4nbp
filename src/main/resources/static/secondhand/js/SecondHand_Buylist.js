let reqaa = null;
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

let del = [];

$('#getshot').next().on('change', function (e) {
    $('#imgView>div').remove();

    let uploadImg = e.target.files || e.dataTransfer.files;
    
    if (!uploadImg.length) {
        return;
    }

    for (let i = 0; i < uploadImg.length; i++) {


        const div = document.createElement('div');
        const img = document.createElement('img');
        const btn = document.createElement('button');
        $(btn).attr('class','del_btn').text('✖');
        const reader = new FileReader();
        if (uploadImg[i].size >=5242880) {
            $(img).addClass('-warning');
        }
        $('#imgView').append(div);
        div.append(img);
        div.append(btn);

        reader.readAsDataURL(uploadImg[i]);
        reader.addEventListener('load', e => {
            img.src = e.target.result;
        });


        btn.addEventListener('click', e =>{
            del.push(i);
            div.remove(); 
        })  

    }



})


$('#commit').on('click', function (e) {
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
                img_list.push({image : file[i].name});
                var xhr = new XMLHttpRequest();
                xhr.open('POST', 'testpic', true);
                xhr.send(formData);
            }
        del = [];

    fetch('addbuyevent', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            productName: productName.value,
            type: type,
            content: content.value,
            estimate: s_estimate,
            applicantBankNumber: applicantBankNumber.value,
            image : img_list
        })
    })
        .then(resp => resp.json())
        .then(obj => {
            checkpage(obj);
        })
        
            
    

      
})







function checkpage(obj) {

    if (obj.buylistId) {
        $('.inputBlock').remove();
        const div = document.createElement('div');
        let type;
        switch (obj.type[0]) {
            case '0':
                type = '其他';
                break;
            case '1':
                type = '任天堂';
                break;
            case '2':
                type = 'Sony';
                break;
            case '3':
                type = 'XBOX';
                break;

            default:
                break;
        }
        type = type + " / "
        switch (obj.type[1]) {
            case '0':
                type = type +'主機';
                break;
            case '1':
                type = type +'手把';
                break;
            case '2':
                type = type +'光碟、卡帶';
                break;
            case '3':
                type = type +'周邊';
                break;

            default:
                break;
        }

        let succ = obj.successful ? '成功' : '失敗' ;

        let approvalState;
        switch (obj.approvalState) {
            case '0':
                approvalState = '待審核';
                break;
                case '1':
                    approvalState ='接受預估價';
                break;
                case '2':
                    approvalState='議價中';
                break;
                case '3':
                    approvalState='非收購品項';
                break;
                case '4':
                    approvalState='不合格品';
                break;
                case '5':
                    approvalState='已退回 ';
                break;
                case '6':
                    approvalState='已完成';
                break;

        
            default:
                break;
        }

        div.innerHTML += `
        <div class="return_title">申請結果</div>
        <hr>
    <div><div>訂單編號 : </div><span>${obj.buylistId}</span></div>
    <div><div>會員編號 : </div><span>${obj.memberId}</span></div>
    <div><div>商品名稱 : </div><span>${obj.productName}</span></div>
    <div><div>種類 : </div><span>${type}</span></div>
    <div><div>內容 : </div><span>${obj.content}</span></div>
    <div><div>銀行帳號 : </div><span>${obj.applicantBankNumber}</span></div>
    <div><div>預估價格 : </div><span>${obj.estimate}</span></div>
    <div><div>是否成功 : </div><span>${succ}</span></div>
    <div><div>審核狀態 : </div><span>${approvalState}</span></div>
    <hr>
    <div class="btnArea">
    <button onclick="gohome(event || window.event)">回首頁</button>
    </div>
    `;
    div.setAttribute('class','return_box');
        $('.showArea').append(div);
        $('.content').attr('style','width : 40%');
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

