(() => {
    const ban_add_btn = document.querySelector('#ban_add_btn');

    const ban_member = document.querySelector('#ban_member');
    const ban_manager = document.querySelector('#ban_manager');
    const ban_reason = document.querySelector('#ban_reason');
    const ban_duration = document.querySelector('#ban_duration');

    let member_id;
    let manager_id;

    let chekedIdList = [];

    fetch('../manager/getName')
        .then(response => response.json())
        .then(data => {
            // 在此處處理後端回傳的資料

            console.log(data);

            member_id = sessionStorage.getItem('member_id');
            manager_id = data.loggedManager_id;

            ban_member.innerHTML = member_id;
            ban_manager.innerHTML = manager_id;
        })
        .catch(error => {
            console.error('Error:', error);
        });



    ban_add_btn.addEventListener('click', () => {

        let banDays = parseInt(ban_duration.value);
        if (isNaN(banDays) || !Number.isInteger(banDays)) {
            msg.textContent = '天數請輸入整數';
            return;
        }


        msg.textContent = '';
        fetch('../manager/ban_readIn', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                member_id: member_id,
                manager_id: manager_id,
                ban_reason: ban_reason.value,
                ban_durationByDay: banDays,
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