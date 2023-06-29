
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


// NS
nMain.addEventListener("click", function (){
    sessionStorage.removeItem("keyword", keywordValue);
    typeValue = "00";
    console.log(typeValue);
    sessionStorage.setItem("type", typeValue);
    window.location.href="../sh_shop/sh_keyTypeMainView.html";

})
nHandle.addEventListener("click", function (){
    sessionStorage.removeItem("keyword", keywordValue);
    typeValue = "01";
    console.log(typeValue)
    sessionStorage.setItem("type", typeValue);
    window.location.href="../sh_shop/sh_keyTypeMainView.html";
})
nGame.addEventListener("click", function (){
    sessionStorage.removeItem("keyword", keywordValue);
    typeValue = "02";
    console.log(typeValue)
    sessionStorage.setItem("type", typeValue);
    window.location.href="../sh_shop/sh_keyTypeMainView.html";
})
nOther.addEventListener("click", function (){
    sessionStorage.removeItem("keyword", keywordValue);
    typeValue = "03";
    console.log(typeValue)
    sessionStorage.setItem("type", typeValue);
    window.location.href="../sh_shop/sh_keyTypeMainView.html";
})

// xbox
bMain.addEventListener("click", function (){
    sessionStorage.removeItem("keyword", keywordValue);
    typeValue = "10";
    console.log(typeValue)
    sessionStorage.setItem("type", typeValue);
    window.location.href="../sh_shop/sh_keyTypeMainView.html";
})
bHandle.addEventListener("click", function (){
    sessionStorage.removeItem("keyword", keywordValue);
    typeValue = "11";
    console.log(typeValue)
    sessionStorage.setItem("type", typeValue);
    window.location.href="../sh_shop/sh_keyTypeMainView.html";
})
bGame.addEventListener("click", function (){
    sessionStorage.removeItem("keyword", keywordValue);
    typeValue = "12";
    console.log(typeValue)
    sessionStorage.setItem("type", typeValue);
    window.location.href="../sh_shop/sh_keyTypeMainView.html";
})
bOther.addEventListener("click", function (){
    sessionStorage.removeItem("keyword", keywordValue);
    typeValue = "13";
    console.log(typeValue)
    sessionStorage.setItem("type", typeValue);
    window.location.href="../sh_shop/sh_keyTypeMainView.html";
})

// PS
pMain.addEventListener("click", function (){
    sessionStorage.removeItem("keyword", keywordValue);
    typeValue = "20";
    console.log(typeValue)
    sessionStorage.setItem("type", typeValue);
    window.location.href="../sh_shop/sh_keyTypeMainView.html";
})
pHandle.addEventListener("click", function (){
    sessionStorage.removeItem("keyword", keywordValue);
    typeValue = "21";
    console.log(typeValue)
    sessionStorage.setItem("type", typeValue);
    window.location.href="../sh_shop/sh_keyTypeMainView.html";
})
pGame.addEventListener("click", function (){
    sessionStorage.removeItem("keyword", keywordValue);
    typeValue = "22";
    console.log(typeValue)
    sessionStorage.setItem("type", typeValue);
    window.location.href="../sh_shop/sh_keyTypeMainView.html";
})
pOther.addEventListener("click", function (){
    sessionStorage.removeItem("keyword", keywordValue);
    typeValue = "23";
    console.log(typeValue)
    sessionStorage.setItem("type", typeValue);
    window.location.href="../sh_shop/sh_keyTypeMainView.html";
})


// 搜尋keyword

keywordInput.addEventListener('keydown', function(e) {
    if( e.keyCode === 13 ){
        sessionStorage.removeItem("type", typeValue);
        keywordValue = keywordInput.value;
        console.log(keywordValue);
        sessionStorage.setItem("keyword", keywordValue);
        window.location.href="../sh_shop/sh_keyTypeMainView.html";
    }
});

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

