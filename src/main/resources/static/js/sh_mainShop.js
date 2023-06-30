const shp1Img = document.querySelector('body > main > div.shopmain > div:nth-child(6) > div > ul > li:nth-child(1) > a:nth-child(1) > img');
const shp1Name = document.querySelector('body > main > div.shopmain > div:nth-child(6) > div > ul > li:nth-child(1) > a.productname');
const shp2Img = document.querySelector('body > main > div.shopmain > div:nth-child(6) > div > ul > li:nth-child(2) > a:nth-child(1) > img');
const shp2Name = document.querySelector('body > main > div.shopmain > div:nth-child(6) > div > ul > li:nth-child(2) > a.productname');
const shp3Img = document.querySelector('body > main > div.shopmain > div:nth-child(6) > div > ul > li:nth-child(3) > a:nth-child(1) > img');
const shp3Name = document.querySelector('body > main > div.shopmain > div:nth-child(6) > div > ul > li:nth-child(3) > a.productname');
const shp4Img = document.querySelector('body > main > div.shopmain > div:nth-child(6) > div > ul > li:nth-child(4) > a:nth-child(1) > img');
const shp4Name = document.querySelector('body > main > div.shopmain > div:nth-child(6) > div > ul > li:nth-child(4) > a.productname');

let shp1ImgValue = shp1Img.value;
let shp1NameValue;

let shpContainer = document.querySelector('body > main > div.shopmain > div:nth-child(6) > div > ul');

let Mainshp_array = [];

showShptime();

function showShptime() {

    fetch('shpView', {
        method: 'GET',
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
                            image: secondhandproduct.image.map(item => item.image),
                        }
                        Mainshp_array[secondhandproduct.productId] = (Mainshp_array_item);
                        console.log(Mainshp_array)
                        showList();
                    }
                )

            }
        )


    function showList() {

        let html = "";
        Mainshp_array.forEach(
            secondhandproduct => {

                html += `
       
                     <li>
                     <a href="">
                     <img src="../${secondhandproduct.image[0]}"alt="商品圖片"></a>
                     <a href="#" className="productname" onclick="clickShp(${secondhandproduct.productId})">${secondhandproduct.name}</a>
                       </li>
       
            `

            }
        );

        shpContainer.innerHTML = html;


    }


}



// // 將 ID 儲存到 sessionStorage 中
function clickShp(productId) {
sessionStorage.setItem('productId', productId);
window.location.href = "../sh_shop/sh_Mall.html";
}