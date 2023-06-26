const orderId = document.getElementById('orderId');
const memberId = document.getElementById('memberId');
const orderPrice = document.getElementById('orderPrice');
const payState = document.getElementById('payState');
const deliverState = document.getElementById('deliverState');
const managerID = document.getElementById('managerID');
const orderListContainer = document.querySelector('table#orderTable tbody');
console.log(orderListContainer);
let order_array = [];

fetch('sh_ordermanage',{
    method:'POST',
    headers:{'Content-Type': 'application/json'},
}).then(resp => resp.json())
.then(jsondata => {
    jsondata.forEach(
        order => {
            let order_array_item = {
                orderId: order.orderId,
                productId: order.productId,
                memberId: order.memberId,
                totalPrice: order.totalPrice,
                payState: order.payState,
                deliverState: order.deliverState,
                managerId: order.managerId,
            };
            order_array[order.orderId] = (order_array_item);
            showList();

        }
    )
})


function showList(){
let html ="";
order_array.forEach(
    order => {
        html += `
        
            <tr>
                <td><span onclick="getProductId(${order.productId}); getOrderId(${order.orderId})"><a id ="orderId" class="SHorderNum" href="#" style="text-decoration:none; color: rgb(93, 87, 87);">${order.orderId}</a></span></td>
                <td><span id="memberId">${order.memberId}</span></td>
                <td>$<span id="orderPrice">${order.totalPrice}</span>元</td>
            
                <td>
                <div>
                    <select id="payState" style="width: 90px;">
                    <option value="0" ${order.payState === 0 ? 'selected' : ''}>未付款</option>
                    <option value="1" ${order.payState === 1 ? 'selected' : ''}>已付款</option>
                    </select>
                </div>
                </td>
                
                <td>
                <div>
                    <select id="deliverState" style="width: 90px;">
                    <option value="0" ${order.deliverState === 0 ? 'selected' : ''}>待出貨</option>
                    <option value="1" ${order.deliverState === 1 ? 'selected' : ''}>出貨中</option>
                    <option value="2" ${order.deliverState === 2 ? 'selected' : ''}>已到貨</option>
                    </select>
                </div>
                </td>
                
                <td><span id="managerID">${order.managerId}</span></td>
            </tr>
        `
    }

)
    orderListContainer.innerHTML = html;

}


// function changePayState(){
//     let stateValue = payState.value;
//
//     payState.addEventListener("change", function (){
//
//         fetch('payState', {
//             method: 'GET',
//             headers: {'Content-Type': 'application/json'},
//             body: JSON.stringify({
//                 productId: shp_array[orderId].productId,
//                 payState: stateValue,
//             })
//
//         }).then(function (){
//             Swal.fire({
//                 position: 'center',
//                 icon: 'success',
//                 title: '上下架狀態已變更',
//                 showConfirmButton: false,
//                 timer: 1500
//             })
//         })
//     })
//
// }



orderListContainer.addEventListener("change", function (){

    if(event.target.id === "payState") {
        let stateValue = event.target.value;
        console.log(stateValue);

        // 获取当前行的 orderId
        let orderId = event.target.closest("tr").querySelector(".SHorderNum").innerText;
        // 確認按鈕有空再更改
        // let confirmOff = confirm("確定變更" + shp_array[productId].name + "上下架狀態嗎?");
        console.log(orderId);

            fetch('payState', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    // managerId: sessionStorage.getItem("logged_id"),
                    orderId: order_array[orderId].orderId,
                    payState: stateValue,
                })

            }).then(
                function () {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: '付款狀態更新',
                        showConfirmButton: false,
                        timer: 1500
                    })
                }

            ).then(()=>{
             setTimeout(()=>{
                 location.reload()
             }, 1800)
                })





    }

    else if(event.target.id === "deliverState") {
        let stateValue = event.target.value;
        console.log(stateValue);

        // 获取当前行的 orderId
        let orderId = event.target.closest("tr").querySelector(".SHorderNum").innerText;
        // 確認按鈕有空再更改
        // let confirmOff = confirm("確定變更" + shp_array[productId].name + "上下架狀態嗎?");
        console.log(orderId);

        fetch('deliverState', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                // managerId: sessionStorage.getItem("logged_id"),
                orderId: order_array[orderId].orderId,
                memberId: order_array[orderId].memberId,
                deliverState: stateValue,
            })

        }).then(
            function () {
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: '出貨狀態更新',
                    showConfirmButton: false,
                    timer: 1500
                })
            }

        ).then(()=>{
            setTimeout(()=>{
                location.reload()
            }, 1800)
        })





    }




})



function getProductId(productId){
    sessionStorage.setItem("productId", productId);
    window.location.href="../member/member_shorderinfo.html";
}

function getOrderId(orderId){
    sessionStorage.setItem("orderId", orderId);
    window.location.href="../member/member_shorderinfo.html";
}