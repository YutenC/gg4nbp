
let Mainshp_array = [];

let MainshpListContainer = document.querySelector('div.product');
console.log(MainshpListContainer);


fetch('shp_main', {
    method: 'get',
    headers: {
        'Content-Type': 'application/json',
    },
})
    .then(resp => resp.json()) // .then(function(resp){resp.json();})
    .then(
        jsondata => {
            console.log("jsondata" + jsondata);
            jsondata.forEach(
                secondhandproduct => {
                    let Mainshp_array_item = {
                        productId: secondhandproduct.productId,
                        name: secondhandproduct.name,
                        image: secondhandproduct.image.map(item => item.image),}
                    Mainshp_array[secondhandproduct.productId] = (Mainshp_array_item);
                    console.log(Mainshp_array)
                            showList();
                }
            )
        }
    )




function btnSubmit() {
    console.log("新增頁面，跳轉頁面");
    window.location.href = "../manager/sh_productmanage.html";
}








function showList() {

            let html = "";
            Mainshp_array.forEach(
                secondhandproduct => {

                    html += `
       
                     <div class="showItem">
                        <div class="showItem_img" onclick="clickShp(${secondhandproduct.productId})">
                            <a href="sh_Mall.html">
                                <img src= "../${secondhandproduct.image[0]}" alt="商品圖片" class="showItem_img">
                            </a>

                        </div>

                        <div class="showItem_name" onclick="clickShp(${secondhandproduct.productId})">
                            <a href="sh_Mall.html" class="showItem_name">
                                 ${secondhandproduct.name}
                            </a>
                        </div>

                        <div class="showItem_buy">
                            <input type="button" value="直接購買" onclick="buyBtn(${secondhandproduct.productId})">
                        </div>

                    </div>
       
            `

                }


            );

            MainshpListContainer.innerHTML = html;


}

// 將 ID 儲存到 sessionStorage 中
function clickShp(productId) {
    sessionStorage.setItem('productId', productId);
    window.location.href = "../sh_shop/sh_Mall.html";
}


function buyBtn(productId) {
    sessionStorage.setItem('productId', productId);
    window.location.href = "../sh_shop/sh_buy.html";
}

