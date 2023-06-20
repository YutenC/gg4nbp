$("input.login_btn1").on("click", () => {
    event.preventDefault

    fetch('manager_logining', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            manager_account: $("input#username").val(),
            manager_password: $("input#password").val()
        })
    })
        .then(resp => resp.json())
        .then(body => {

            console.log(body);
            const { successful, redirectUrl } = body;

            if (successful) {
                alert("登入成功");
                const { manager_id, account } = body;

                sessionStorage.setItem('logged_id', manager_id);
                sessionStorage.setItem('logged_account', account);

                window.location.href = redirectUrl;

                if (redirectUrl) {
                    window.location.href = redirectUrl; // 進行重導
                }

            } else {
                alert("登入失敗")
            }
        })
})

$("input").on("keydown", (event) => {
    if (event.key === "Enter") {
        event.preventDefault();
        $("input.login_btn1").click(); // 模拟点击登录按钮
    }
});
