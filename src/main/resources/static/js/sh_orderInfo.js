const deliverSelect = document.getElementById('deliverSelect');
const paySelect = document.getElementById('paySelect');
const deliverWayContainer = document.getElementById('deliverWayContainer');
const payWayContainer = document.getElementById('payWayContainer');

const memAccount = document.getElementById('from_name');
const memPhone = document.getElementById('from_tel');
const memEmail = document.getElementById('from_email');

const shpName = document.getElementById('shpName');
const shpPrice = document.getElementById('shpPrice');

const deliverName = document.getElementById('deliverName');
const deliverAddress = document.getElementById('deliverAddress');
const payInfo1 = document.getElementById('payInfo1');
const payInfo2 = document.getElementById('payInfo2');


const totalPriceText = document.getElementById('totalPrice');
const deliverFeeText = document.getElementById('deliverFee');

// const buyPage = document.getElementById('buyPage');


// // ===========選擇取貨方式=============
// deliverSelect.addEventListener("change", function (){
//     let deliverValue = deliverSelect.value;
//     if(deliverValue === "1"){
//
//     let html=`
//
//      <div class="form-label">
//         <label class="pay_bank">請填入收件資訊</label>
//         <div>收件人</div><input id="deliName" type="text" class="form-control">
//         <div>收件地址</div><input id="deliAddress" type="text" class="form-control">
//      </div>
//
//     `;
//
//         deliverWayContainer.innerHTML = html;
//         deliverFee.innerHTML = "100";
//
//     } else if(deliverValue === "2"){
//
//         let html=`
//
//      <div class="form-label">
//         <label class="pay_market">請填入取貨超商</label>
//         <div>收件人</div><input id="deliName" type="text" class="form-control">
//         <div>超取門市及店號（Ex: 全家-12345-NPG門市）</div><input id="deliAddress" type="text" class="form-control">
//     </div>
//
//     `;
//
//         deliverWayContainer.innerHTML = html;
//         deliverFee.innerHTML = "60";
//     }
//
//     // totalPrice.innerText = (30 + parseFloat(deliverFee.innerText)).toString();
//
//     // 選擇運送方式跳轉總金額
//     fetch('priceChange', {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json',
//         }
//         ,
//         body: JSON.stringify({
//             productId: sessionStorage.getItem('productId'),
//             price: shpPrice.value,
//             receive: deliverValue
//         }),
//     })
//         .then(resp => resp.json()) // .then(function(resp){resp.json();})
//         .then(function (total) {
//                 console.log("取得DB資料")
//                 console.log(total);
//                 total = total.toString();
//                 totalPrice.innerHTML = total;
//             }
//
//         )
//
// })
//
//
// // ===========選擇付款方式=============
//
// paySelect.addEventListener("change", function (){
//     let payValue = paySelect.value;
//     if(payValue === "1"){
//
//         let html=`
//
//      <div class="form-label">
//         <label class="pay_bank">請填入匯款資訊</label>
//         <div>銀行名稱</div><input id="bankName" type="text" class="form-control">
//         <div>匯款帳號</div><input id="bankNum" type="text" class="form-control">
//      </div>
//
//     `;
//
//         payWayContainer.innerHTML = html;
//
//     } else if(payValue === "2"){
//
//         let html=`
//
//      <div class="form-label">
//         <label class="pay_credit">請填入信用卡資訊</label>
//         <div>銀行名稱</div><input id="bankName" type="text" class="form-control">
//         <div>信用卡卡號</div><input id="bankNum" type="text" class="form-control">
//      </div>
//
//     `;
//
//         payWayContainer.innerHTML = html;
//
//     } else if(payValue === "3"){
//
//         let html=``;
//
//         payWayContainer.innerHTML = html;
//
//     }
//
//
// })





// 取得會員&商品基本資訊

fetch('sh_orderInfo', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
    }
    ,
    body: JSON.stringify({
        // member: sessionStorage.getItem('id'),
        productId: sessionStorage.getItem('productId'),
        orderId: sessionStorage.getItem('orderId')
    }),
})
    .then(resp => resp.json()) // .then(function(resp){resp.json();})
    .then(function (objects) {
             [ member= {
                account,
                phone,
                email
            }, shp = {
                name,
                price
            }, od = {
                deliverName,
                deliverLocation,
                payBank,
                payNumber,
                deliverFee,
                totalPrice
            }] = objects;

            console.log("取得DB資料")

            memAccount.value = member.account;
            memPhone.value = member.phone;
            memEmail.value = member.email;

            shpName.innerHTML = shp.name;
            shpPrice.innerHTML = shp.price;


        deliverName.value = od.deliverName;
        deliverAddress.value = od.deliverLocation;

        // console.log("payBank" + od.payBank);


        if(od.payBank === undefined && od.payNumber === undefined){
            let payBankValue = "超商付款";
            let payNumberValue = "超商付款";
            console.log("payBank111" + payBankValue);
            payInfo1.value = payBankValue;
            payInfo2.value = payNumberValue;
        } else {
            let payBankValue = od.payBank;
            let payNumberValue = od.payNumber;
            console.log("payBank222" + payBankValue);
            payInfo1.value = payBankValue;
            payInfo2.value = payNumberValue;

        }


        // payInfo1.value = payBankValue;
        // payInfo2.value = payNumberValue;

        let deliverFeeValue = parseInt(od.deliverFee);
        let totalPriceValue = parseInt(od.totalPrice);
        deliverFeeText.innerHTML = deliverFeeValue;
        totalPriceText.innerHTML = totalPriceValue;




        }

    )





//
// // 點擊送出訂單
// submitBtn.addEventListener("click", function(e) {
//
//     console.log(submitBtn);
//
//     // 送出變更會員資料
//     let phoneValue = memPhone.value;
//     let emailValue = memEmail.value;
//     console.log(phoneValue);
//     console.log(emailValue);
//
//         fetch('/gg4nbp/member/orderMemChange', {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json',
//             }
//             ,
//             body: JSON.stringify({
//                 account: sessionStorage.getItem('id'),
//                 phone: phoneValue,
//                 email: emailValue,
//             })
//         })
//
//
//     // 送出訂單資料
//     let receiveValue = parseInt($('#deliverSelect').val());
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
//             // memberId: sessionStorage.getItem('id'),
//             receive: receiveValue,
//             deliverName: deliNameValue,
//             deliverLocation: deliAddressValue,
//             payBank: bankNameValue,
//             payNumber: bankNumValue,
//
//         })
//     })
//
//      window.location.href="../sh_shop/sh_MainView.html"
//
//     })






// 重新寫controller進入修改
// 把value傳回後端 運費輸入到後端 再做運算