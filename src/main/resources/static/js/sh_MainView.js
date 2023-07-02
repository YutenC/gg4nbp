
let Mainshp_array = [];

let MainshpListContainer = document.querySelector('div.product');
console.log(MainshpListContainer);

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

const keywordInput = document.querySelector('#input_search > input[type=text]');
let keywordValue = keywordInput.value;

const type_btn = document.querySelectorAll('.listtype>span');
for (let i = 0; i < type_btn.length; i++) {
    let type1 = Math.floor(i / 4) +""+ i % 4 ; 
    type_btn[i].addEventListener('click',function(){
        sessionStorage.removeItem("keyword")
        goSearch(type1);
    })
}
    





// // NS
// nMain.addEventListener("click", function (){
//     typeValue = "00";
//     goSearch(typeValue);

// })
// nHandle.addEventListener("click", function (){
//     typeValue = "01";
//     sessionStorage.removeItem("keyword", keywordValue);
//     sessionStorage.removeItem("type");
//     console.log(typeValue)
//     sessionStorage.setItem("type", typeValue);
//     history.go(0);
// })
// nGame.addEventListener("click", function (){
//     sessionStorage.removeItem("keyword", keywordValue);
//     sessionStorage.removeItem("type");
//     typeValue = "02";
//     console.log(typeValue)
//     sessionStorage.setItem("type", typeValue);
//     history.go(0);
// })
// nOther.addEventListener("click", function (){
//     sessionStorage.removeItem("keyword", keywordValue);
//     sessionStorage.removeItem("type");
//     typeValue = "03";
//     console.log(typeValue)
//     sessionStorage.setItem("type", typeValue);
//     history.go(0);
// })

// // xbox
// bMain.addEventListener("click", function (){
//     sessionStorage.removeItem("keyword", keywordValue);
//     sessionStorage.removeItem("type");
//     typeValue = "10";
//     console.log(typeValue)
//     sessionStorage.setItem("type", typeValue);
//     history.go(0);
// })
// bHandle.addEventListener("click", function (){
//     sessionStorage.removeItem("keyword", keywordValue);
//     sessionStorage.removeItem("type");
//     typeValue = "11";
//     console.log(typeValue)
//     sessionStorage.setItem("type", typeValue);
//     history.go(0);
// })
// bGame.addEventListener("click", function (){
//     sessionStorage.removeItem("keyword", keywordValue);
//     sessionStorage.removeItem("type");
//     typeValue = "12";
//     console.log(typeValue)
//     sessionStorage.setItem("type", typeValue);
//     history.go(0);
// })
// bOther.addEventListener("click", function (){
//     sessionStorage.removeItem("keyword", keywordValue);
//     sessionStorage.removeItem("type");
//     typeValue = "13";
//     console.log(typeValue)
//     sessionStorage.setItem("type", typeValue);
//     history.go(0);
// })

// // PS
// pMain.addEventListener("click", function (){
//     sessionStorage.removeItem("keyword", keywordValue);
//     sessionStorage.removeItem("type");
//     typeValue = "20";
//     console.log(typeValue)
//     sessionStorage.setItem("type", typeValue);
//     history.go(0);
// })
// pHandle.addEventListener("click", function (){
//     sessionStorage.removeItem("keyword", keywordValue);
//     sessionStorage.removeItem("type");
//     typeValue = "21";
//     console.log(typeValue)
//     sessionStorage.setItem("type", typeValue);
//     history.go(0);
// })
// pGame.addEventListener("click", function (){
//     sessionStorage.removeItem("keyword", keywordValue);
//     sessionStorage.removeItem("type");
//     typeValue = "22";
//     console.log(typeValue)
//     sessionStorage.setItem("type", typeValue);
//     history.go(0);
// })
// pOther.addEventListener("click", function (){
//     sessionStorage.removeItem("keyword", keywordValue);
//     sessionStorage.removeItem("type");
//     typeValue = "23";
//     console.log(typeValue)
//     sessionStorage.setItem("type", typeValue);
//     history.go(0);
// })


// 搜尋keyword

keywordInput.addEventListener('keydown', function(e) {
    if( e.keyCode === 13 ){
        sessionStorage.removeItem("type", typeValue);
        keywordValue = keywordInput.value;
        console.log(keywordValue);
        sessionStorage.setItem("keyword", keywordValue);
        location.href ='SecondHand_MainView.html';
    }
});




if(sessionStorage.getItem("type")){
// 從資料庫取種類商品
    fetch('shp_typeSearch', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            type: sessionStorage.getItem("type", typeValue)
            // type: typeValue
        }),
    })
        .then(resp => resp.json()) // .then(function(resp){resp.json();})
        .then(
            jsondata => {

                console.log("typeSelect" + jsondata);
                jsondata.forEach(
                    secondhandproduct => {
                        let Mainshp_array_item = {
                            productId: secondhandproduct.productId,
                            name: secondhandproduct.name,
                            type: secondhandproduct.type,
                            image: secondhandproduct.image.map(item => item.image),
                        }
                        Mainshp_array[secondhandproduct.productId] = (Mainshp_array_item);
                        console.log(Mainshp_array);
                        showList();
                    }
                )

            }
        )

}else if(sessionStorage.getItem("keyword")){

// 從資料庫取關鍵字商品
    fetch('shp_keywordSearch', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name: sessionStorage.getItem("keyword", keywordValue)
        }),
    })
        .then(resp => resp.json()) // .then(function(resp){resp.json();})
        .then(
            jsondata => {

                console.log("keywordSelect" + jsondata);
                jsondata.forEach(
                    secondhandproduct => {
                        let Mainshp_array_item = {
                            productId: secondhandproduct.productId,
                            name: secondhandproduct.name,
                            type: secondhandproduct.type,
                            image: secondhandproduct.image.map(item => item.image),
                        }
                        Mainshp_array[secondhandproduct.productId] = (Mainshp_array_item);
                        console.log(Mainshp_array);
                        showList();
                    }
                )

            }
        )
}else{
    // 全部上架商品
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

}























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
    window.location.href = "../member/sh_buy.html";
}



function goSearch(str){
    sessionStorage.removeItem("keyword", keywordValue);
    sessionStorage.removeItem("type");
    sessionStorage.setItem("type", str);
    location.href ="SecondHand_MainView.html";
}