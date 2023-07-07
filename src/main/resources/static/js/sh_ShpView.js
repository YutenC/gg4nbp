const SHproName = document.querySelector('.productName');
const SHproPrice = document.querySelector("div.price>span");
const SHproContent = document.querySelector('.description_detail');
const shpImg0 = document.getElementById('small0');
const shpImg1 = document.getElementById('small1');
const shpImg2 = document.getElementById('small2');
const shpImg3 = document.getElementById('small3');

const buyBtn = document.querySelector('.direct_buybtn');

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


buyBtn.addEventListener("click", function (){
    window.location.href = "../member/sh_buy.html";
})