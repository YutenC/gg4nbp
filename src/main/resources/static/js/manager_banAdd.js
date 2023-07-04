(() => {
    const ban_add_btn = document.querySelector('#ban_add_btn');

    const ban_member = document.querySelector('#ban_member');
    const ban_manager = document.querySelector('#ban_manager');
    const ban_reason = document.querySelector('#ban_reason');
    const ban_duration = document.querySelector('#ban_duration');

    let member_id;
    let manager_id;

    fetch('../manager/getName')
        .then(response => response.json())
        .then(data => {
            // 在此處處理後端回傳的資料

            member_id = sessionStorage.getItem('member_id');
            manager_id = data.loggedManager_id;

            ban_member.innerHTML = member_id;
            ban_manager.innerHTML = manager_id;
        })
        .catch(error => {
            console.error('Error:', error);
        });



    ban_add_btn.addEventListener('click', () => {


        if (!/^-?\d+$/.test(ban_duration.value)) {
            Swal.fire({
                title: "天數錯誤！",
                text: "停權天數只可輸入正負整數",
                icon: "error"
            });
            return;
        }

        let banDays = parseInt(ban_duration.value);

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

                const { successful, redirectUrl } = body;

                if (successful) {
                    Swal.fire({
                        title: "停權成功",
                        icon: "success",
                        didClose: () => {
                            if (redirectUrl) {
                                window.location.href = redirectUrl; // 进行重定向
                            }
                        }
                    });

                } else {
                    Swal.fire({
                        title: "停權失敗",
                        icon: "error"
                    });

                }

            });



    });

    manager_add_btn_cancel.addEventListener("click", () => {
        window.location.href = "manager_memList.html";
    })
})();