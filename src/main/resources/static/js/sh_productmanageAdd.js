let del = [];

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



    // ======圖片選擇與刪除=====================



    // const photoUploadBtn = document.getElementById('photoUploadBtn');

    photoUploadBtn.addEventListener('click', function (e) {
        $(this).next().click();
    })

    $('#photoUploadBtn').next().on('change', function (e) {
        $('#imgView>div').remove();

        let uploadImg = e.target.files || e.dataTransfer.files;

        if (!uploadImg.length) {
            return;
        }

        for (let i = 0; i < uploadImg.length; i++) {


            const div = document.createElement('div');
            const img = document.createElement('img');
            const btn = document.createElement('button');
            $(btn).attr('class', 'del_btn').text('✖');
            const reader = new FileReader();
            if (uploadImg[i].size >= 5242880) {
                $(img).addClass('-warning');
            }
            $('#imgView').append(div);
            div.append(img);
            div.append(btn);

            reader.readAsDataURL(uploadImg[i]);
            reader.addEventListener('load', e => {
                img.src = e.target.result;
            });


            btn.addEventListener('click', e => {
                del.push(i);
                div.remove();
            })

        }})

    // ======圖片選擇與刪除END=====================

        $('#photoUploadBtn2').on('change',()=>{
            del = [];
        })

    // 加入商品
    addBtn.addEventListener("click", function () {

        // =========圖片=========
        let img_list = [];
        const file = $('#photoUploadBtn').next()[0].files;
        filter:
            for (let i = 0; i < file.length; i++) {
                const formData = new FormData();

                for (let j = 0; j < del.length; j++) {
                    if (i === del[j]) {
                        continue filter;
                    }
                }
                formData.append(file[i].name, file[i]);
                img_list.push({image : file[i].name});
                const xhr = new XMLHttpRequest();
                xhr.open('POST', 'testpic', true);
                xhr.send(formData);
            }
        del = [];
        // =========圖片=========
        console.log(img_list);


        fetch('sh_productmanageAdd', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                name: SHproName.value,
                type: SHproType.value,
                price: SHproPrice.value,
                content: SHproContent.value,
                image: img_list
                // SHproPho1: SHproPho1.value,
            }),
        })
            .then(resp => resp.json()) // .then(function(resp){resp.json();})
            .then(function (body) {
                    console.log(body);
                    const {successful} = body;
                    if (successful) {
                        // btnSubmit();

                        console.log("state=" + successful);
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: '商品新增上架完成',
                            showConfirmButton: false,
                            timer: 1500
                        })

                        setTimeout(function() {
                            window.location.href = "../manager/sh_productmanage.html";
                        }, 1800);

                    } else {
                        Swal.fire({
                            icon: 'error',
                            title: '新增失敗',
                            text: '內容都需填寫喔',
                            // footer: '<a href="">Why do I have this issue?</a>'
                        });
                    }

                }
            )
    })



    function btnSubmit() {
        console.log("新增頁面，跳轉頁面");
        window.location.href = "../manager/sh_productmanage.html";
    }


})();


