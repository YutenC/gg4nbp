let od_array = [];

let orderListContainer = document.querySelector('table#orderTable tbody');
console.log(orderListContainer);


fetch('shp_allOrder', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
    },
})
    .then(resp => resp.json()) // .then(function(resp){resp.json();})
    .then(

        objects => {
            objects.forEach(
                secondhandorder => {
                    let od_array_item = {
                        productId: secondhandorder.productId,
                        orderDate: secondhandorder.orderDate,
                        orderId: secondhandorder.orderId,
                        totalPrice: secondhandorder.totalPrice,
                        deliverState: secondhandorder.deliverState,
                    };
                    od_array[secondhandorder.orderId] = (od_array_item);
                    showList();
                }
            )

        }
    )



function showList() {

    let html = "";
    let NO = 0;


    od_array.forEach(
        secondhandorder => {

            //======= 出貨(訂單)狀態代碼判斷 =======
            let deliverValue = secondhandorder.deliverState;
            switch (deliverValue) {
                case 0:
                    deliverValue = "待出貨";
                    break;
                case 1:
                    deliverValue = "出貨中";
                    break;
                case 2:
                    deliverValue = "已到貨";
                    break;
            }

            // ===== 修改日期格式 =====
            var dateFormat = new Date(secondhandorder.orderDate).toLocaleDateString('zh-TW');


            // ===== 訂單list資料 =====
            html += `
       
             <tr>

                                <td><span>NO.${++NO}</span></td>

                                <td><span>${dateFormat}</span></td>

                                <td>
                                    <span>${secondhandorder.orderId}</span>
                                </td>

<!--                                <td>-->
<!--                                    <div class="shpro_name">-->
<!--                                        <span>Switch舊款初代整套</span>-->
<!--                                    </div>-->
<!--                                </td>-->

                                <td>
                                    <div class="shpro_price">
                                        $<span>${secondhandorder.totalPrice}</span>元
                                    </div>
                                </td>

                                <td>
                                    <div class="shorder_status">
                                        <span>${deliverValue}</span>
                                    </div>
                                </td>
                                
                                <td>
                                    <div class="orderDetail" onclick="getProductId(${secondhandorder.productId}); getOrderId(${secondhandorder.orderId})">
                                        <a href="#" style="text-decoration:none; color: rgb(64, 61, 61);">查看詳情</a>
                                    </div>
                                </td>

                            </tr>
            
            `

        }
    );

   orderListContainer.innerHTML = html;

}

function getProductId(productId){
    sessionStorage.setItem("productId", productId);
    window.location.href="../member/member_shorderinfo.html";
}

function getOrderId(orderId){
    sessionStorage.setItem("orderId", orderId);
    window.location.href="../member/member_shorderinfo.html";
}