(() => {
    const edit_btn = document.querySelector('#manager_edit_btn');

    const manager_account = document.querySelector('#manager_account');
    const manager_password = document.querySelector('#manager_password');
    const manager_name = document.querySelector('#manager_name');
    const manager_email = document.querySelector('#manager_email');
    const manager_phone = document.querySelector('#manager_phone');
    const manager_address = document.querySelector('#manager_address');

    const inputs = document.querySelectorAll('input');

    let manager_id;
    let manager_is_working;

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

            console.log(data);
            console.log(data.manager);

            manager_account.value = data.manager.account;
            manager_password.value = data.manager.password;
            manager_name.value = data.manager.name;
            manager_email.value = data.manager.email;
            manager_phone.value = data.manager.phone;
            manager_address.value = data.manager.address;

            manager_id = data.manager.manager_id;
            manager_is_working = data.manager.manager_is_working;


        })
        .catch(error => {
            console.error('Error:', error);
        });


    edit_btn.addEventListener('click', () => {
        const accLength = manager_account.value.length;
        if (accLength < 5 || accLength > 50) {
            msg.textContent = '帳號長度須介於5~50字元';
            return;
        }

        const pwdLength = manager_password.value.length;
        if (pwdLength < 6 || pwdLength > 12) {
            msg.textContent = '密碼長度須介於6~12字元';
            return;
        }

        const nameLength = manager_name.value.length;
        if (nameLength < 1 || nameLength > 20) {
            msg.textContent = '暱稱長度須介於1~20字元';
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
                manager_account: manager_account.value,
                manager_password: manager_password.value,
                manager_name: manager_name.value,
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



})();