//
// 1. 先抓html元素
// 2. 先寫showlist
// 3. 再寫修改
// 4. 再寫刪除
// 5. mySQL增加上架欄位
//
let shp_array = [];

let shpListContainer = document.querySelector('table#sh_dataTable tbody');
console.log(shpListContainer);


    fetch('sh_productmanage', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(resp => resp.json()) // .then(function(resp){resp.json();})
        .then(

            jsondata => {
                jsondata.forEach(
                    secondhandproduct => {
                        let shp_array_item = {
                            productId: secondhandproduct.productId,
                            name: secondhandproduct.name,
                            type: secondhandproduct.type,
                            price: secondhandproduct.price,
                            isLaunch: secondhandproduct.isLaunch,
                            // launchTime: secondhandproduct.launchTime
                        };
                        shp_array[secondhandproduct.productId] = (shp_array_item);
                        showList();
                    }
                )
            }
        )


function sphDelete(productId){

        // let confirmDelete = () => Swal.fire({
        //     title: '確定刪除嗎？',
        //     // text: "You won't be able to revert this!",
        //     icon: 'warning',
        //     showCancelButton: true,
        //     confirmButtonColor: '#3085d6',
        //     cancelButtonColor: '#d33',
        //     confirmButtonText: 'YES'
        // }).then((result) => {
        //     if (result.isConfirmed) {
        //         Swal.fire(
        //             '商品已刪除',
        //             // 'Your file has been deleted.',
        //             'success'
        //         )
        //     }
        // })

    // 確認按鈕有空再更改
    let confirmDelete = confirm("確定刪除" + shp_array[productId].productId + "嗎?");


    if(confirmDelete){
        fetch('shp_delete',{
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                productId: shp_array[productId].productId,
                name: shp_array[productId].name,
                type: shp_array[productId].type,
                price: shp_array[productId].price,
                isLaunch: shp_array[productId].isLaunch,

                // launchTime: shp_array[productId].launchTime
            })
        }).then(
            btnSubmit()
        )

    }
}

function btnSubmit() {
    console.log("新增頁面，跳轉頁面");
    window.location.href = "../manager/sh_productmanage.html";


}


function showList() {

    let html = "";
    shp_array.forEach(
        secondhandproduct => {

            html += `
       
            <tr>
                                                                               <td>
                                                                                 <div>
                                                                                       <select class="saleornot" style="width: 90px;">
                                                                                          <option value="" disabled></option> 
                                                                                          <option value="0" selected>下架</option>
                                                                                            <option value="${secondhandproduct.isLaunch}">上架</option>
                                                                                     </select>
                                                                                 </div>
                                                                                </td>

            <td><span>
            <!--之後連上架的商品頁面-->
            <a class="SHproNum" href="#"   
                style="text-decoration:none; color: rgb(93, 87, 87);">${secondhandproduct.productId}</a>
            </span></td>

            <td><span class="SHproName">${secondhandproduct.name}</span></td>
            <td><span class="SHproType">${secondhandproduct.type}</span></td>
            <td>$<span class="SHproPrice">${secondhandproduct.price}</span>元</td>
<!--            解開註解記得將\刪除-->
<!--            <td><span class="SHproLaunchTime">\${secondhandproduct.launchTime}"</span></td>-->

            <td>
                 <div class="game_label">
                      <button type="button" onclick="sphEdit(${secondhandproduct.productId})" class="btn btn-success">修改</button>
                      <button type="button" onclick="sphDelete(${secondhandproduct.productId})" class="btn btn-warning">刪除</button>
                 </div>
            </td>

            <td><span class="managerID">Manager1990</span></td>
            </tr>
            
            `

        }
    );

    shpListContainer.innerHTML = html;

}

// 將 ID 儲存到 sessionStorage 中
function sphEdit(productId) {
    sessionStorage.setItem('productId', productId);
    window.location.href = "../manager/sh_productmanageEdit.html";
}