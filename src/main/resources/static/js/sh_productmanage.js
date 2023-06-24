//
// 1. 先抓html元素
// 2. 先寫showlist
// 3. 再寫修改
// 4. 再寫刪除
// 5. mySQL增加上架欄位
//
const saleState = document.getElementById('saleState');
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
    let confirmDelete = confirm("確定刪除商品ID：" + shp_array[productId].productId + "嗎?");


    if(confirmDelete){
        fetch('shp_delete',{
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                productId: shp_array[productId].productId,
                name: shp_array[productId].name,
                type: shp_array[productId].type,
                price: shp_array[productId].price,
                // isLaunch: shp_array[productId].isLaunch,
                // launchTime: shp_array[productId].launchTime
            })
        }).then(
            function () {
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: '商品已刪除',
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
}

// function btnSubmit() {
//     console.log("新增頁面，跳轉頁面");
//     window.location.href = "../manager/sh_productmanage.html";
// }


function showList() {

    let html = "";


    shp_array.forEach(
        secondhandproduct => {

            //======= type代碼判斷 =======
            let typeValue = secondhandproduct.type;
            switch (typeValue) {
                case "00":
                    typeValue = "NS主機";
                    break;
                case "01":
                    typeValue = "NS手把";
                    break;
                case "02":
                    typeValue = "NS卡帶";
                    break;
                case "03":
                    typeValue = "NS周邊";
                    break;
                case "10":
                    typeValue = "XBOX主機";
                    break;
                case "11":
                    typeValue = "XBOX手把";
                    break;
                case "12":
                    typeValue = "XBOX遊戲片";
                    break;
                case "13":
                    typeValue = "XBOX周邊";
                    break;
                case "20":
                    typeValue = "PS主機";
                    break;
                case "21":
                    typeValue = "PS手把";
                    break;
                case "22":
                    typeValue = "PS遊戲片";
                    break;
                case "23":
                    typeValue = "PS周邊";
                    break;
            }



            html += `
       
            <tr>
                <td>
                 <div>
                     <select id="saleState" style="width: 90px;">
                     <option value="" disabled></option> 
                     <option value="0" ${secondhandproduct.isLaunch === "0" ? 'selected' : ''}>下架</option>
                     <option value="1" ${secondhandproduct.isLaunch === "1" ? 'selected' : ''}>上架</option>
                     </select>
                </div>
                </td>

            <td><span>
            <!--之後連上架的商品頁面-->
            <a class="SHproNum" href="#"   
                style="text-decoration:none; color: rgb(93, 87, 87);">${secondhandproduct.productId}</a>
            </span></td>

            <td><span class="SHproName">${secondhandproduct.name}</span></td>
            <td><span class="SHproType">${typeValue}</span></td>
            <td>$<span class="SHproPrice">${secondhandproduct.price}</span>元</td>
<!--            解開註解記得將\刪除-->
<!--             <td><span class="SHproLaunchTime">\${secondhandproduct.launchTime}</span></td>-->

            <td>
                 <div class="game_label">
                      <button type="button" onclick="sphEdit(${secondhandproduct.productId})" class="btn btn-success">修改</button>
                      <button type="button" onclick="sphDelete(${secondhandproduct.productId})" class="btn btn-warning">刪除</button>
                 </div>
            </td>

<!--            <td><span class="managerID">Manager1990</span></td>-->
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



shpListContainer.addEventListener("change", function (){

    if(event.target.id === "saleState") {
        let stateValue = event.target.value;
        console.log(stateValue);

        // 获取当前行的 productId
        let productId = event.target.closest("tr").querySelector(".SHproNum").innerText;
        // 確認按鈕有空再更改
        // let confirmOff = confirm("確定變更" + shp_array[productId].name + "上下架狀態嗎?");
        console.log(productId);

            fetch('shp_launch', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    productId: shp_array[productId].productId,
                    isLaunch: stateValue,
                })

            }).then(function (){
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: '上下架狀態已變更',
                    showConfirmButton: false,
                    timer: 1500
                })


            })

    }






})