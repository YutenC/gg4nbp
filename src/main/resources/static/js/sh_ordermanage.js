const orderId = document.getElementById('orderId');
const memberId = document.getElementById('memberId');
const orderPrice = document.getElementById('orderPrice');
const payState = document.getElementById('payState');
const delieverState = document.getElementById('delieverState');
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
                <td><span><a id ="orderId" class="SHorderNum" href="#" style="text-decoration:none; color: rgb(93, 87, 87);">${order.orderId}</a></span></td>
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
                    <option value="0" ${order.deliverState === 0 ? 'selected' : ''}>未出貨</option>
                    <option value="1" ${order.deliverState === 1 ? 'selected' : ''}>已出貨</option>
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