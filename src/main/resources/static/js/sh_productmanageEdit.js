(() => {

    const SHproName = document.getElementById('SHproName');
    // 也可以寫成這樣: const SHproName = document.querySelector('#SHproName');

    const SHproType = document.getElementById('SelectSHproType');
    // const value = SHproType.value;

    const SHproPrice = document.getElementById('SHproPrice');
    const SHproContent = document.getElementById('SHproContent');
    const SHproPho = document.getElementById('SHproPho');
    const SHproPho1 = document.getElementById('SHproPho1');
    const SHproPho2 = document.getElementById('SHproPho2');
    const SHproPho3 = document.getElementById('SHproPho3');
    const SHproPho4 = document.getElementById('SHproPho4');
    const photoUploadBtn = document.getElementById('photoUploadBtn');
    const photoDeleteBtn = document.getElementById('photoDeleteBtn');
    const saveBtn = document.getElementById('saveBtn');
    const addBtn = document.getElementById('addBtn');

    const errorName = document.getElementById('errorName');
    const errorType = document.getElementById('errorType');
    const errorPrice = document.getElementById('errorPrice');
    const errorContent = document.getElementById('errorContent');
    const errorPhoto = document.getElementById('errorPhoto');

    // 測試是否抓到ID
    // launchBtn.addEventListener("click", function (){
    //     const shname = SHproName.value;
    //     console.log(shname);})


    // 錯誤訊息設定
    SHproName.addEventListener("blur", function () {
        const SHproValue = SHproName.value;
        if (SHproValue.trim() === "") {
            errorName.textContent = "請輸入商品名稱";
        } else {
            errorName.textContent = "";
        }
    })


    SHproType.addEventListener("blur", function () {
        const SHtypeValue = SHproType.value;
        if (SHtypeValue === "") {
            errorType.textContent = "請選擇商品類別";
        } else {
            errorType.textContent = "";
        }
    })


    SHproPrice.addEventListener("blur", function () {
        const SHpriceValue = SHproPrice.value;
        if (SHpriceValue === "") {
            errorPrice.textContent = "請輸入價格";
        } else if (SHpriceValue <= 0) {
            errorPrice.textContent = "價格不得為0或小於0元";
        } else {
            errorPrice.textContent = "";
        }
    })

    SHproContent.addEventListener("blur", function () {
        const SHcontentValue = SHproContent.value;
        if (SHcontentValue === "") {
            errorContent.textContent = "請輸入商品內容";
        } else {
            errorContent.textContent = "";
        }
    })


// 讀取資料庫檔案到頁面
    fetch('shp_Select', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            productId: sessionStorage.getItem('productId')
            // name: SHproName.value,
            // type: SHproType.value,
            // price: SHproPrice.value,
            // content: SHproContent.value,
            // SHproPho1: SHproPho1.value,
        }),
    })
        .then(resp => resp.json()) // .then(function(resp){resp.json();})
        .then(function (body) {
                let {
                    name,
                    type,
                    price,
                    content,
                } = body;

                console.log("傳入session並回傳取得DB資料")

                SHproName.value = name;
                SHproType.value = type;
                SHproPrice.value = price;
                SHproContent.value = content;
                // SHproPho1.value

            }

    )


    fetch('shpImg_Select', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            productId: sessionStorage.getItem('productId')

        }),
    })
        .then(resp => resp.json()) // .then(function(resp){resp.json();})
        .then(function (uploadImg) {
            console.log(uploadImg);
                // for(let file of uploadImg){
                //     console.log(file);
                //     console.log("傳入session並回傳取得DB資料")
                //
                // }


            // $('#photoUploadBtn').next().on('change', function (e) {
            //     $('#imgView>div').remove();
                // uploadImg = e.target.files || e.dataTransfer.files;

                if (!uploadImg.length) {
                    return;
                }

                for (let i = 0; i < uploadImg.length; i++) {

                    // const imageUrl = URL.createObjectURL(uploadImg[i]);

                    const div = document.createElement('div');
                    const img = document.createElement('img');
                    const btn = document.createElement('button');
                    $(btn).attr('class', 'del_btn').text('✖');
                    // const reader = new FileReader();

                    $('#imgView').append(div);
                    div.append(img);
                    div.append(btn);

                    // reader.readAsDataURL(uploadImg[i]);
                    // reader.addEventListener('load', e => {
                    //     console.log("目標="+e.target.result)
                    //     img.src = e.target.result;
                    // });

                    img.src = ".."+ uploadImg[i];
                    console.log(img.src);

                    btn.addEventListener('click', e => {
                        del.push(i);
                        div.remove();
                    })

                }}

        // )
            // }

        )

    // 修改商品內容
    saveBtn.addEventListener("click", function () {
        console.log("saveBtn");
        fetch('shp_Edit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                productId: sessionStorage.getItem('productId'),
                name: SHproName.value,
                type: SHproType.value,
                price: SHproPrice.value,
                content: SHproContent.value,
                // SHproPho1: SHproPho1.value,
            }),
        }).then(resp => resp.json()) // .then(function(resp){resp.json();})
            .then(function (body) {
                console.log(body);
                const {successful} = body;
                if (successful) {
                    console.log("物件修改成功")
                    saveSubmit();
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: '更新失敗',
                        text: '內容都需填寫喔',
                        // footer: '<a href="">Why do I have this issue?</a>'
                    });
                }
                }
            )
    })


    function saveSubmit() {
        console.log("更新成功，跳轉頁面");
        window.location.href = "../manager/sh_productmanage.html";
    }



})();