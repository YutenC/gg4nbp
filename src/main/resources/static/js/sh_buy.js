const deliverSelect = document.getElementById('deliverSelect');
const paySelect = document.getElementById('paySelect');
const deliverWayContainer = document.getElementById('deliverWayContainer');
const payWayContainer = document.getElementById('payWayContainer');

const memAccount = document.getElementById('from_name');
const memPhone = document.getElementById('from_tel');
const memEmail = document.getElementById('from_email');

const shpName = document.getElementById('shpName');
const shpPrice = document.getElementById('shpPrice');
const totalPrice = document.getElementById('totalPrice');

const deliverFee = document.getElementById('deliverFee');

// const buyPage = document.getElementById('buyPage');
const buyPage = document.getElementsByClassName('buyDirectlyPage');
const submitBtn = document.getElementById('sendOrder');


// ===================================================================
// 側邊欄選項元素
const nMain = document.querySelector('body > main > div.side > div:nth-child(1) > div > div:nth-child(1) > span');
const nHandle = document.querySelector('body > main > div.side > div:nth-child(1) > div > div:nth-child(2) > span');
const nGame = document.querySelector('body > main > div.side > div:nth-child(1) > div > div:nth-child(3) > span');
const nOther = document.querySelector('body > main > div.side > div:nth-child(1) > div > div:nth-child(4) > span');

const bMain = document.querySelector('body > main > div.side > div:nth-child(2) > div > div:nth-child(1) > span');
const bHandle = document.querySelector('body > main > div.side > div:nth-child(2) > div > div:nth-child(2) > span');
const bGame = document.querySelector('body > main > div.side > div:nth-child(2) > div > div:nth-child(3) > span');
const bOther = document.querySelector('body > main > div.side > div:nth-child(2) > div > div:nth-child(4) > span');

const pMain = document.querySelector('body > main > div.side > div:nth-child(3) > div > div:nth-child(1) > span');
const pHandle = document.querySelector('body > main > div.side > div:nth-child(3) > div > div:nth-child(2) > span');
const pGame = document.querySelector('body > main > div.side > div:nth-child(3) > div > div:nth-child(3) > span');
const pOther = document.querySelector('body > main > div.side > div:nth-child(3) > div > div:nth-child(4) > span');

// pOther.addEventListener("click", function (){console.log("nnn")});
// console.log("click");



var typeValue;



// NS
nMain.addEventListener("click", function (){
    typeValue = "00";
    console.log(typeValue);
    sessionStorage.setItem("type", typeValue);
    window.location.href="../sh_shop/sh_TypeMainView.html";

})
nHandle.addEventListener("click", function (){
    typeValue = "01";
    console.log(typeValue)
    sessionStorage.setItem("type", typeValue);
    window.location.href="../sh_shop/sh_TypeMainView.html";
})
nGame.addEventListener("click", function (){
    typeValue = "02";
    console.log(typeValue)
    sessionStorage.setItem("type", typeValue);
    window.location.href="../sh_shop/sh_TypeMainView.html";
})
nOther.addEventListener("click", function (){
    typeValue = "03";
    console.log(typeValue)
    sessionStorage.setItem("type", typeValue);
    window.location.href="../sh_shop/sh_TypeMainView.html";
})

// xbox
bMain.addEventListener("click", function (){
    typeValue = "10";
    console.log(typeValue)
    sessionStorage.setItem("type", typeValue);
    window.location.href="../sh_shop/sh_TypeMainView.html";
})
bHandle.addEventListener("click", function (){
    typeValue = "11";
    console.log(typeValue)
    sessionStorage.setItem("type", typeValue);
    window.location.href="../sh_shop/sh_TypeMainView.html";
})
bGame.addEventListener("click", function (){
    typeValue = "12";
    console.log(typeValue)
    sessionStorage.setItem("type", typeValue);
    window.location.href="../sh_shop/sh_TypeMainView.html";
})
bOther.addEventListener("click", function (){
    typeValue = "13";
    console.log(typeValue)
    sessionStorage.setItem("type", typeValue);
    window.location.href="../sh_shop/sh_TypeMainView.html";
})

// PS
pMain.addEventListener("click", function (){
    typeValue = "20";
    console.log(typeValue)
    sessionStorage.setItem("type", typeValue);
    window.location.href="../sh_shop/sh_TypeMainView.html";
})
pHandle.addEventListener("click", function (){
    typeValue = "21";
    console.log(typeValue)
    sessionStorage.setItem("type", typeValue);
    window.location.href="../sh_shop/sh_TypeMainView.html";
})
pGame.addEventListener("click", function (){
    typeValue = "22";
    console.log(typeValue)
    sessionStorage.setItem("type", typeValue);
    window.location.href="../sh_shop/sh_TypeMainView.html";
})
pOther.addEventListener("click", function (){
    typeValue = "23";
    console.log(typeValue)
    sessionStorage.setItem("type", typeValue);
    window.location.href="../sh_shop/sh_TypeMainView.html";
})
// ===================================================================








// ===========選擇取貨方式=============
deliverSelect.addEventListener("change", function (){
    let deliverValue = deliverSelect.value;
    if(deliverValue === "1"){

    let html=`
    
     <div class="form-label">
        <label class="pay_bank">請填入收件資訊</label>
        <div>收件人</div><input id="deliName" type="text" class="form-control">
        <div>收件地址</div><input id="deliAddress" type="text" class="form-control">
     </div>
    
    `;

        deliverWayContainer.innerHTML = html;
        deliverFee.innerHTML = "100";

    } else if(deliverValue === "2"){

        let html=`
  
     <div class="form-label">
        <label class="pay_market">請填入取貨超商</label>
        <div>收件人</div><input id="deliName" type="text" class="form-control">
        <div>超取門市及店號（Ex: 全家-12345-NPG門市）</div><input id="deliAddress" type="text" class="form-control">
    </div>
    
    `;

        deliverWayContainer.innerHTML = html;
        deliverFee.innerHTML = "60";
    }

    // totalPrice.innerText = (30 + parseFloat(deliverFee.innerText)).toString();

    // 選擇運送方式跳轉總金額
    fetch('priceChange', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        }
        ,
        body: JSON.stringify({
            productId: sessionStorage.getItem('productId'),
            price: shpPrice.value,
            receive: deliverValue
        }),
    })
        .then(resp => resp.json()) // .then(function(resp){resp.json();})
        .then(function (total) {
                console.log("取得DB資料")
                console.log(total);
                total = total.toString();
                totalPrice.innerHTML = total;
            }

        )

})


// ===========選擇付款方式=============

paySelect.addEventListener("change", function (){
    let payValue = paySelect.value;
    if(payValue === "1"){

        let html=`
    
     <div class="form-label">
        <label class="pay_bank">請填入匯款資訊</label>
        <div>銀行名稱</div><input id="bankName" type="text" class="form-control">
        <div>匯款帳號</div><input id="bankNum" type="text" class="form-control">
     </div>
    
    `;

        payWayContainer.innerHTML = html;

    } else if(payValue === "2"){

        let html=`
  
     <div class="form-label">
        <label class="pay_credit">請填入信用卡資訊</label>
        <div>銀行名稱</div><input id="bankName" type="text" class="form-control">
        <div>信用卡卡號</div><input id="bankNum" type="text" class="form-control">
     </div>
    
    `;

        payWayContainer.innerHTML = html;

    } else if(payValue === "3"){

        let html=``;

        payWayContainer.innerHTML = html;

    }


})





// 取得會員&商品基本資訊

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






// 點擊送出訂單
submitBtn.addEventListener("click", function(e) {

    console.log(submitBtn);

    // 送出變更會員資料
    let phoneValue = memPhone.value;
    let emailValue = memEmail.value;
    console.log(phoneValue);
    console.log(emailValue);

        fetch('/gg4nbp/member/orderMemChange', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            }
            ,
            body: JSON.stringify({
                account: sessionStorage.getItem('id'),
                phone: phoneValue,
                email: emailValue,
            })
        })


    // 送出訂單資料
    let receiveValue = parseInt($('#deliverSelect').val());
    let deliNameValue = $('#deliName').val();
    let deliAddressValue = $('#deliAddress').val();
    let bankNameValue = $('#bankName').val();
    let bankNumValue = $('#bankNum').val();


    fetch('/gg4nbp/sh_shop/shp_buy', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        }
        ,
        body: JSON.stringify({
            productId: sessionStorage.getItem('productId'),
            // memberId: sessionStorage.getItem('id'),
            receive: receiveValue,
            deliverName: deliNameValue,
            deliverLocation: deliAddressValue,
            payBank: bankNameValue,
            payNumber: bankNumValue,

        })
    })

     window.location.href="../sh_shop/sh_MainView.html"

    })



// $('#deliverSelect, #paySelect').on('change', function (){
//
//     let deliNameValue = $('#deliName').val();
//     let deliAddressValue = $('#deliAddress').val();
//     let bankNameValue = $('#bankName').val();
//     let bankNumValue = $('#bankNum').val();
//
//     console.log("選項變化")
//
// })


// $('#submitBtn').on('click', function (){
//
//     let deliNameValue = $('#deliName').val();
//     let deliAddressValue = $('#deliAddress').val();
//     let bankNameValue = $('#bankName').val();
//     let bankNumValue = $('#bankNum').val();
//
//
//     fetch('shp_buy', {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json',
//         }
//         ,
//         body: JSON.stringify({
//             productId: sessionStorage.getItem('productId'),
//             memberId: sessionStorage.getItem('id'),
//             deliverName: deliNameValue,
//             deliverLocation: deliAddressValue,
//             payName: bankNameValue,
//             payNumber: bankNumValue,
//         })
//     })
//
// })



 // submitBtn.addEventListener("click", function(e) {
 //    console.log(submitBtn);
 //    let phoneValue;
 //    let emailValue;
 //
 //    memPhone.addEventListener("input", function () {
 //        phoneValue = memPhone.value;
 //        // console.log(phoneValue);
 //        fetch('/gg4nbp/member/orderMemChange', {
 //            method: 'POST',
 //            headers: {
 //                'Content-Type': 'application/json',
 //            }
 //            ,
 //            body: JSON.stringify(
 //                 {
 //                    account: sessionStorage.getItem('id'),
 //                    phone: phoneValue}
 //                )
 //        })
 //    })
 //
 //    memEmail.addEventListener("input", function () {
 //        emailValue = memEmail.value;
 //        // console.log(emailValue);
 //
 //        fetch('/gg4nbp/member/orderMemChange', {
 //            method: 'POST',
 //            headers: {
 //                'Content-Type': 'application/json',
 //            }
 //            ,
 //            body: JSON.stringify(
 //                 {
 //                    account: sessionStorage.getItem('id'),
 //                    email: emailValue
 //                })
 //        })
 //
 //    })


















// // 會員修改資料
//
// let deliverValue;
// let deliNameValue;
// let deliAddressValue;
//
// // memEmail.addEventListener('input', function(e){
// //     emailValue = e.target.value;
// // })
// //
// memPhone.addEventListener("change", function(){
//     phoneValue = memPhone.value;
//     // console.log(phoneValue);
// })
//
//
// deliverWayContainer.addEventListener("change", function (e){
//     // const targetElement = e.target;
//     // deliverValue = targetElement.getElementById('deliverSelect').value;
//     deliverValue = e.target.value;
//     // deliNameValue = targetElement.getElementById('deliName').value;
//     // deliAddressValue = targetElement.getElementById('deliAddress').value;
// })
//
//
// console.log(phoneValue);
// // console.log(deliNameValue);
// console.log(deliverValue);
//
// // // 送出訂單
// submitBtn.addEventListener("click", function (){
//
// // ==== 修改會員資料 ====
//
//
//     fetch('/gg4nbp/member/memberEditInforServlet', {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json',
//         }
//         ,
//         body: JSON.stringify(request => [
//             member = {
//                 account: sessionStorage.getItem('id'),
//                 email: emailValue,
//                 value: phoneValue,
//         }, order = {
//                 deliverState: deliverValue,
//                 deliverName,
//                 deliverLocation,
//                 payState,
//                 payBank,
//                 payNumber,
//                 totalPrice
//             }] )
//     })
//
//     console.log("test" + emailValue)
//     console.log("submitBtn")
//
// })






// 重新寫controller進入修改
// 把value傳回後端 運費輸入到後端 再做運算