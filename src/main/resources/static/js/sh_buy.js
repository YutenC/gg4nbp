const deliverSelect = document.getElementById('deliverSelect');
const paySelect = document.getElementById('paySelect');
const deliverWayContainer = document.getElementById('deliverWayContainer');
const payWayContainer = document.getElementById('payWayContainer');

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

    } else if(deliverValue === "2"){

        let html=`
  
     <div class="form-label">
        <label class="pay_market">請填入取貨超商</label>
        <div>超商名稱</div><input type="text" class="form-control">
<!--        <div>超商代碼</div><input type="text">-->
    </div>
    
    `;

        deliverWayContainer.innerHTML = html;

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
        <div>信用卡卡號</div><input type="text" class="form-control">
     </div>
    
    `;

        payWayContainer.innerHTML = html;

    } else if(payValue === "3"){

        let html=``;

        payWayContainer.innerHTML = html;

    }




})