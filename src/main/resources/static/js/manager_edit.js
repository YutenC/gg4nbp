(() => {
    const edit_btn = document.querySelector('#manager_edit_btn');

    const manager_account = document.querySelector('#manager_account');
    const manager_password = document.querySelector('#manager_password');
    const manager_name = document.querySelector('#manager_name');
    const manager_email = document.querySelector('#manager_email');
    const manager_phone = document.querySelector('#manager_phone');
    const manager_address = document.querySelector('#manager_address');

    let pomSettingDiv = document.querySelector('div.pomSetting');

    let manager_id;
    let manager_is_working;

    let power_array = [];
    let pomAll_array = [];
    let pom_array = [];
    let chekedIdList = [];

    fetch('../manager/getManagerEditInfo', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ id: sessionStorage.getItem('id') })
    })
        .then(response => {
            return response.json();
        })
        .then(data => {
            // 在此處處理後端回傳的資料

            manager_account.innerHTML = data.manager.account;
            manager_password.value = data.manager.password;
            manager_name.innerHTML = data.manager.name;
            manager_email.value = data.manager.email;
            manager_phone.value = data.manager.phone;
            manager_address.value = data.manager.address;

            manager_id = data.manager.manager_id;
            manager_is_working = data.manager.manager_is_working;

            fetch('../manager/power_list', {
                method: 'GET',
            })
                .then(response => response.json())
                .then(data => {

                    // 獲取到 managerList 的資料後，動態生成 array 內容
                    data.powerList.forEach(power => {
                        let power_array_item = {
                            power_id: power.power_id,
                            name: power.power_name,
                            content: power.power_content,
                        }
                        power_array.push(power_array_item);
                    })

                    fetch('../manager/pom_list', {
                        method: 'GET',
                    })
                        .then(response => response.json())
                        .then(data => {

                            // 獲取到 managerList 的資料後，動態生成 array 內容
                            data.pomList.forEach(pom => {
                                let pomAll_array_item = {
                                    manager_id: pom.manager_id,
                                    power_id: pom.power_id,
                                }
                                pomAll_array.push(pomAll_array_item);
                            })

                            pom_array = pomAll_array.filter(item => item.manager_id === manager_id);

                            pomSettingDiv.innerHTML = `<p>管理員權限</p>`;
                            power_array.forEach(power => {

                                if (pom_array !== undefined && pom_array.some(item => item.power_id === power.power_id)) {
                                    pomSettingDiv.innerHTML +=
                                        `
                                <input class="pomCheckbox ${power.power_id}" type="checkbox" id="${power.power_id}"
                                    name="${power.power_id}" value="${power.power_id}" checked>
                                <label for="${power.power_id}">${power.name}</label>
                                `;
                                } else {
                                    pomSettingDiv.innerHTML +=
                                        `
                                <input class="pomCheckbox ${power.power_id}" type="checkbox" id="${power.power_id}"
                                    name="${power.power_id}" value="${power.power_id}">
                                <label for="${power.power_id}">${power.name}</label>
                                `;
                                }
                            });

                        })
                        .catch(error => {
                            console.error('Error:', error);
                        });


                })
                .catch(error => {
                    console.error('Error:', error);
                });

        })
        .catch(error => {
            console.error('Error:', error);
        });



    edit_btn.addEventListener('click', () => {

        let error = {
            text: "",
            title: "輸入錯誤"
        };
        const pwdLength = manager_password.value.length;
        if (pwdLength < 6 || pwdLength > 32) {
            error.title = "密碼錯誤";
            error.text = '密碼長度須介於6~32字元';
            errorTypeAlert(error);
            return;
        }


        msg.textContent = '';
        fetch('../manager/manager_edit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                manager_id: manager_id,
                manager_account: manager_account.innerHTML,
                manager_password: manager_password.value,
                manager_name: manager_name.innerHTML,
                manager_email: manager_email.value,
                manager_phone: manager_phone.value,
                manager_address: manager_address.value,
                manager_is_working: manager_is_working
            }),
        })
            .then(resp => resp.json())
            .then(body => {

                console.log(body);
                const { successful, redirectUrl } = body;

                let pomCheckbox = document.querySelectorAll('input.pomCheckbox');
                pomCheckbox.forEach(checkbox => {
                    if (checkbox.checked) {
                        chekedIdList.push(checkbox.value);
                    }
                });

                fetch('../manager/pom_edit', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        manager_id: manager_id,
                        chekedIdList: chekedIdList,

                    }),
                })
                    .then(resp => resp.json())
                    .then(body => {

                        console.log(body);
                        const { successful, redirectUrl } = body;

                        if (successful) {
                            Swal.fire({
                                title: "修改成功",
                                icon: "success",
                                didClose: () => {
                                    if (redirectUrl) {
                                        window.location.href = redirectUrl; // 进行重定向
                                    }
                                }
                            });
                        } else {
                            error.title = '修改失敗';
                            errorTypeAlert(error);
                        }
                    });
            });
    });
})();

function errorTypeAlert(error) {
    Swal.fire({
        title: error.title,
        text: error.text,
        icon: "error"
    });
}