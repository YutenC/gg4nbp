const deliverSelect = document.getElementById('deliverSelect');
const paySelect = document.getElementById('paySelect');
const deliverWayContainer = document.getElementById('deliverWayContainer');
const payWayContainer = document.getElementById('payWayContainer');

const memAccount = document.getElementById('from_name');
const memPhone = document.getElementById('from_tel');
const memEmail = document.getElementById('from_email');

const shpName = document.getElementById('shpName');
const shpPrice = document.getElementById('shpPrice');

const deliverFee = document.getElementById('deliverFee');

// const buyPage = document.getElementById('buyPage');
const buyPage = document.getElementsByClassName('buyDirectlyPage');
const submitBtn = document.getElementById('sendOrder');


// ===========選擇取貨方式=============
deliverSelect.addEventListener("change", function (){
    const deliverValue = deliverSelect.value;
    if(deliverValue === "1"){

    let html=`
    
     <div class="form-label">
        <label class="pay_bank">請填入收件資訊</label>
        <div>收件人</div><input type="text" class="form-control">
        <div>收件地址</div><input type="text" class="form-control">
     </div>
    
    `;

        deliverWayContainer.innerHTML = html;
        deliverFee.innerHTML = "100";

    } else if(deliverValue === "2"){

        let html=`
  
     <div class="form-label">
        <label class="pay_market">請填入取貨超商</label>
        <div>收件人</div><input type="text" class="form-control">
        <div>超取門市及店號（Ex: 全家-12345-NPG門市）</div><input type="text" class="form-control">
    </div>
    
    `;

        deliverWayContainer.innerHTML = html;
        deliverFee.innerHTML = "60";
    }
})


// ===========選擇付款方式=============

paySelect.addEventListener("change", function (){
    const payValue = paySelect.value;
    if(payValue === "1"){

        let html=`
    
     <div class="form-label">
        <label class="pay_bank">請填入匯款資訊</label>
        <div>銀行名稱</div><input type="text" class="form-control">
        <div>匯款帳號</div><input type="text" class="form-control">
     </div>
    
    `;

        payWayContainer.innerHTML = html;

    } else if(payValue === "2"){

        let html=`
  
     <div class="form-label">
        <label class="pay_credit">請填入信用卡資訊</label>
        <div>銀行名稱</div><input type="text" class="form-control">
        <div>信用卡卡號</div><input type="text" class="form-control">
     </div>
    
    `;

        payWayContainer.innerHTML = html;

    } else if(payValue === "3"){

        let html=``;

        payWayContainer.innerHTML = html;

    }


})





// 取得基本資訊

fetch('/gg4nbp/member/preBuy', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
    }
    ,
    body: JSON.stringify({
        member: sessionStorage.getItem('id'),
        productId: sessionStorage.getItem('productId')
    }),
})
    .then(resp => resp.json()) // .then(function(resp){resp.json();})
    .then(function (objects) {
            let [ member= {
                account,
                phone,
                email
            }, shp = {
                name,
                price
            }] = objects;

            console.log("取得DB資料")

            memAccount.value = member.account;
            memPhone.value = member.phone;
            memEmail.value = member.email;

            shpName.innerHTML = shp.name;
            shpPrice.innerHTML = shp.price;
        }

    )


// 會員修改資料
let emailValue;
let phoneValue;

memEmail.addEventListener('input', function(e){
    emailValue = e.target.value;
})

memPhone.addEventListener('input', function(e){
    phoneValue = e.target.value;
})



// // 送出訂單
submitBtn.addEventListener("click", function (){

// ==== 修改會員資料 ====


    fetch('/gg4nbp/member/memberEditInforServlet', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        }
        ,
        body: JSON.stringify({

            account: sessionStorage.getItem('id'),
            email: emailValue,
            value: phoneValue,
        })
    })

    console.log("test" + emailValue)
    console.log("submitBtn")

})






// 重新寫controller進入修改
// 把value傳回後端 運費輸入到後端 再做運算