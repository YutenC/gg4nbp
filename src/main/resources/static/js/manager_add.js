(() => {
    const add_btn = document.querySelector('#manager_add_btn');
    const cancel_btn = document.querySelector('#manager_add_btn_cancel');

    const manager_account = document.querySelector('#manager_account');
    const manager_password = document.querySelector('#manager_password');
    const manager_confirm_password = document.querySelector('#manager_confirm_password');
    const manager_name = document.querySelector('#manager_name');
    const manager_email = document.querySelector('#manager_email');
    const manager_phone = document.querySelector('#manager_phone');
    const manager_address = document.querySelector('#manager_address');

    let pomSettingDiv = document.querySelector('div.pomSetting');

    let power_array = [];

    const inputs = document.querySelectorAll('input');

    let chekedIdList = [];

    document.addEventListener("DOMContentLoaded", () => {

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

                pomSettingDiv.innerHTML = `<p>管理員權限</p>`;
                power_array.forEach(power => {
                    console.log(power);

                    pomSettingDiv.innerHTML +=
                        `
                        <input class="pomCheckbox ${power.power_id}" type="checkbox" id="${power.power_id}"
                            name="${power.power_id}" value="${power.power_id}">
                        <label for="${power.power_id}">${power.name}</label>
                        `;
                })
            })
            .catch(error => {
                console.error('Error:', error);
            });

    });


    add_btn.addEventListener('click', () => {
        const accLength = manager_account.value.length;
        if (accLength < 5 || accLength > 32) {
            msg.textContent = '帳號長度須介於5-32字元';
            return;
        }

        const pwdLength = manager_password.value.length;
        if (pwdLength < 6 || pwdLength > 32) {
            msg.textContent = '密碼長度須介於6~32字元';
            return;
        }

        if (manager_confirm_password.value !== manager_password.value) {
            msg.textContent = '密碼與確認密碼不相符';
            return;
        }

        const nameLength = manager_name.value.length;
        if (nameLength < 1 || nameLength > 20) {
            msg.textContent = '暱稱長度須介於1~20字元';
            return;
        }

        msg.textContent = '';
        fetch('../manager/manager_add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                manager_account: manager_account.value,
                manager_password: manager_password.value,
                manager_name: manager_name.value,
                manager_email: manager_email.value,
                manager_phone: manager_phone.value,
                manager_address: manager_address.value,
            }),
        })
            .then(resp => resp.json())
            .then(body => {

                console.log(body);
                const { successful, redirectUrl, message, manager_id } = body;

                // if (successful) {
                //     alert(message);

                //     if (redirectUrl) {
                //         window.location.href = redirectUrl; // 進行重導
                //     }

                // } else {
                //     alert(message);
                // }

                let pomCheckbox = document.querySelectorAll('input.pomCheckbox');
                pomCheckbox.forEach(checkbox => {
                    if (checkbox.checked) {
                        chekedIdList.push(checkbox.value);
                    }
                });
                console.log(chekedIdList);

                fetch('../manager/pom_add', {
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
                            alert("成功");

                            if (redirectUrl) {
                                window.location.href = redirectUrl; // 進行重導
                            }

                        } else {
                            msg.className = 'error';
                            msg.textContent = '修改失敗';
                        }


                    });


            });
    });

    cancel_btn.addEventListener('click', () => {
        alert("HI2");
        window.location.href = "../test";
    })

})();