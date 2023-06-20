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

    const inputs = document.querySelectorAll('input');


    add_btn.addEventListener('click', () => {
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
                const { successful, redirectUrl, message } = body;

                if (successful) {
                    alert(message);

                    if (redirectUrl) {
                        window.location.href = redirectUrl; // 進行重導
                    }

                } else {
                    alert(message);
                }
            });
    });

    cancel_btn.addEventListener('click', () => {
        alert("HI2");
        window.location.href = "../test";
    })

})();