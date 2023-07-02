const SHproName = document.querySelector('.productName');
const SHproPrice = document.querySelector("div.price>span");
const SHproContent = document.querySelector('.description_detail');
const shpImg0 = document.getElementById('small0');
const shpImg1 = document.getElementById('small1');
const shpImg2 = document.getElementById('small2');
const shpImg3 = document.getElementById('small3');
//
// const buyBtn = document.querySelector('.direct_buybtn');

// // type與keyword搜尋
//
// const keywordInput = document.querySelector('#input_search > input[type=text]');
// let keywordValue = keywordInput.value;
//
// const type_btn = document.querySelectorAll('.listtype>span');
// for (let i = 0; i < type_btn.length; i++) {
//     let type1 = Math.floor(i / 4) +""+ i % 4 ;
//     type_btn[i].addEventListener('click',function(){
//         goSearch(type1);
//     })
// }
//
// keywordInput.addEventListener('keydown', function(e) {
//     if( e.keyCode === 13 ){
//         sessionStorage.removeItem("type", typeValue);
//         keywordValue = keywordInput.value;
//         console.log(keywordValue);
//         sessionStorage.setItem("keyword", keywordValue);
//         history.go(0);
//     }
// });
//
// fetch('shp_keywordSearch', {
//     method: 'POST',
//     headers: {
//         'Content-Type': 'application/json',
//     },
//     body: JSON.stringify({
//         name: sessionStorage.getItem("keyword", keywordValue)
//     }),
// })
//     .then(resp => resp.json()) // .then(function(resp){resp.json();})
//     .then(
//         jsondata => {
//
//             console.log("keywordSelect" + jsondata);
//             jsondata.forEach(
//                 secondhandproduct => {
//                     let Mainshp_array_item = {
//                         productId: secondhandproduct.productId,
//                         name: secondhandproduct.name,
//                         type: secondhandproduct.type,
//                         image: secondhandproduct.image.map(item => item.image),
//                     }
//                     Mainshp_array[secondhandproduct.productId] = (Mainshp_array_item);
//                     console.log(Mainshp_array);
//                     showList();
//                 }
//             )
//
//         }
//     )
//
// function goSearch(str){
//     sessionStorage.removeItem("keyword", keywordValue);
//     sessionStorage.removeItem("type");
//     sessionStorage.setItem("type", str);
//     location.href ="SecondHand_MainView.html";
// }
// // ==================================


// if(!sessionStorage.getItem("type")) {

    fetch('shp_view', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            productId: sessionStorage.getItem('productId')

        }),
    })
        .then(resp => resp.json()) // .then(function(resp){resp.json();})
        .then(function (body) {
                let {
                    name,
                    price,
                    content,
                    image,
                } = body;

                console.log("傳入session並回傳取得DB資料")

                SHproName.innerHTML = name;
                SHproPrice.innerHTML = price;
                SHproContent.innerHTML = content;
                const urlList = body.image.map(item => item.image);
                shpImg0.setAttribute('src', `..${urlList[0]}`)
                shpImg1.setAttribute('src', `..${urlList[1]}`)
                shpImg2.setAttribute('src', `..${urlList[2]}`)
                shpImg3.setAttribute('src', `..${urlList[3]}`)


            }
        )
// }

// buyBtn.addEventListener("click", function (){
//     window.location.href = "../member/sh_buy.html";
// })

// // 將 ID 儲存到 sessionStorage 中
// function clickShp(productId) {
//     sessionStorage.setItem('productId', productId);
//     window.location.href = "../secondhand/sh_Mall.html";
// }

// 圖片點選替換
function replaceImage(id) {
    // console.log("clickkkk")
    const small0 = document.getElementById('small0');
    const smallImage = document.getElementById(id);

    const small0Src = small0.getAttribute('src');
    const smallImageSrc = smallImage.getAttribute('src');

    small0.setAttribute('src', smallImageSrc);
    smallImage.setAttribute('src', small0Src);
}

