let member_array = [];
let filtered_array = [];
let filtered = false;
let memberListContainer = document.querySelector('table#member_list tbody');

// 使用 AJAX 發送請求獲取 memberList 的資料

fetch('../manager/member_list', {
    method: 'GET',
})
    .then(response => response.json())
    .then(data => {

        // 獲取到 memberList 的資料後，動態生成 array 內容
        data.memberList.forEach(member => {

            let member_state = "";
            if (member.member_ver_state === 0) {
                member_state = "待驗證";
            } else if (member.member_ver_state === 2) {
                member_state = "停權中";
            } else if (member.member_ver_state === 3) {
                member_state = "永久停權";
            } else {
                member_state = "已驗證";
            }


            let member_array_item = {
                member_id: member.member_id,
                account: member.account,
                password: member.password,
                nick: member.nick,
                email: member.email,
                phone: member.phone,
                birth: member.birth,
                id_number: member.id_number,
                address: member.address,
                bonus: member.bonus,
                member_ver_state: member_state,
                suspend_deadline: (member.suspend_deadline ?? ""),
                headshot: member.headshot,
                ver_deadline: member.ver_deadline,
                violation: member.violation
            }
            member_array[member.member_id] = (member_array_item);

        })


        showList();

    })
    .catch(error => {
        console.error('Error:', error);
    });

// WIP




// $("a.manager_search_button").on("click", () => {
//     event.preventDefault();
//     filtered = true;

//     let searchType = $("select.manager_search_type").val();
//     let searchContent = $("input.manager_search_content").val();

//     // alert(typeof searchType + searchType + "\n" + typeof searchContent + searchContent);

//     filtered_array = member_array.filter((manager) => {
//         if (typeof manager[searchType] === "number") {
//             let searchContent_num = /^[0-9]+$/.test(searchContent) ? parseInt(searchContent) : NaN;
//             if (searchContent_num === NaN) {
//                 alert("該欄只能搜尋數字");
//             } else {
//                 // console.log(typeof manager[searchType]);
//                 // console.log(typeof searchContent);

//                 return manager[searchType].toString().includes(searchContent);
//             }
//         }
//         return manager[searchType].includes(searchContent);
//     });

//     // console.log(filtered_array);
//     showList();

// })

// $("a.manager_default_list_button").on("click", () => {
//     event.preventDefault();
//     filtered = false;
//     showList();
// })



// function changeStateClick(id) {
//     event.preventDefault();

//     let confirmChangeWorkingState = confirm("確定更改" + member_array[id].account + "的在職狀況嗎?")

//     // 使用 AJAX 發送請求，將 ID 值傳送到後端
//     if (confirmChangeWorkingState) {
//         fetch('../manager/manager_state_edit', {
//             method: 'POST',
//             headers: { 'Content-Type': 'application/json' },
//             body: JSON.stringify({
//                 manager_id: member_array[id].manager_id,
//                 manager_account: member_array[id].account,
//                 manager_password: member_array[id].password,
//                 manager_name: member_array[id].name,
//                 manager_email: member_array[id].email,
//                 manager_phone: member_array[id].phone,
//                 manager_address: member_array[id].address,
//                 manager_is_working: member_array[id].is_working
//             })
//         })
//             .then(response => {
//                 if (response.redirected) {
//                     window.location.href = response.url; // 重導至指定的 URL
//                 } else {
//                     return response.json(); // 解析 JSON 回應
//                 }
//             })
//             .catch(error => {
//                 console.error('Error:', error);
//             });
//     }

// }

function banReadIn(member_id) {
    // 將 ID 儲存到 sessionStorage 中
    sessionStorage.setItem('member_id', member_id);
}

function banClear(member_id) {
    fetch('../manager/getName')
        .then(response => response.json())
        .then(data => {

            // 在此處處理後端回傳的資料
            manager_id = data.loggedManager_id;

            let banDays = -99999;

            Swal.fire({
                title: "解除停權",
                text: `會員ID${member_id}，確認解除停權嗎?`,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: '是',
                cancelButtonText: '否'
            }).then((result) => {
                if (result.isConfirmed) {
                    fetch('../manager/ban_clear', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify({
                            member_id: member_id,
                            manager_id: manager_id,
                            ban_reason: "手動取消停權",
                            ban_durationByDay: banDays,
                        }),
                    })
                        .then(resp => resp.json())
                        .then(body => {

                            const { successful, redirectUrl } = body;

                            if (successful) {
                                Swal.fire({
                                    title: '已解除停權',
                                    text: `會員ID"` + member_id + `"已經解除停權`,
                                    icon: 'success',
                                    didClose: () => {
                                        if (redirectUrl) {
                                            window.location.href = redirectUrl; // 进行重定向
                                        }
                                    }
                                });
                            } else {
                                Swal.fire({
                                    title: '動作失敗',
                                    text: `請洽相關人員`,
                                    icon: 'error',
                                });
                            }
                        });
                }
            });
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function banForever(member_id) {
    fetch('../manager/getName')
        .then(response => response.json())
        .then(data => {
            // 在此處處理後端回傳的資料
            manager_id = data.loggedManager_id;

            let banDays = 99999;

            Swal.fire({
                title: "永久停權",
                text: `會員ID${member_id}，確認永久停權嗎?`,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: '是',
                cancelButtonText: '否'
            }).then((result) => {
                if (result.isConfirmed) {
                    fetch('../manager/ban_forever', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify({
                            member_id: member_id,
                            manager_id: manager_id,
                            ban_reason: "手動取消停權",
                            ban_durationByDay: banDays,
                        }),
                    })
                        .then(resp => resp.json())
                        .then(body => {

                            console.log(body);
                            const { successful, redirectUrl } = body;

                            if (successful) {
                                Swal.fire({
                                    title: '已永久停權',
                                    text: `會員ID"` + member_id + `"已經永久停權`,
                                    icon: 'success',
                                    didClose: () => {
                                        if (redirectUrl) {
                                            window.location.href = redirectUrl; // 进行重定向
                                        }
                                    }
                                });
                            } else {
                                Swal.fire({
                                    title: '動作失敗',
                                    text: `請洽相關人員`,
                                    icon: 'error',
                                });
                            }
                        });
                }
            });
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function showList() {
    let showArray = member_array;
    if (filtered) {
        showArray = filtered_array;
    }

    let html = '';

    showArray.forEach(member => {



        //     let verStateHtml = '';
        //     if (member. === "在職") {
        //         verStateHtml = `
        // <a class="btn btn-primary btn-sm d-none d-sm-inline-block is-workingState"
        //     role="button"
        //     onclick="changeStateClick(${manager.manager_id})">
        //     ${manager.is_working}
        // </a>`
        //     } else {
        //         verStateHtml = `
        // <a class="btn btn-primary btn-sm d-none d-sm-inline-block isNot-workingState"
        //     role="button"
        //     onclick="changeStateClick(${manager.manager_id})">
        //     ${manager.is_working}
        // </a>`
        //     }

        let BanHtml = ''
        if (member.member_ver_state !== "永久停權") {
            BanHtml = `
            <a class="btn btn-primary btn-sm d-none d-sm-inline-block custom-member-button btn-danger"
                role="button" onclick="banReadIn(${member.member_id})" href="manager_banAdd.html">
                發起停權單
            </a>
            `;
        }

        let cancelBanHtml = ''
        if (member.member_ver_state === "停權中" || member.member_ver_state === "永久停權") {
            cancelBanHtml = `
        <a class="btn btn-primary btn-sm d-none d-sm-inline-block custom-member-button"
        role="button" onclick="banClear(${member.member_id})">
        取消停權
        </a>
            `;
        }

        let foreverBanHtml = ''
        if (member.member_ver_state !== "永久停權") {
            foreverBanHtml = `
        <a class="btn btn-primary btn-sm d-none d-sm-inline-block custom-member-button btn-danger"
        role="button" onclick="banForever(${member.member_id})">
        永久停權
        </a>
            `;
        }





        html += `
<tr>
    <td>${member.member_id}</td>
    <td>${member.account}</td>
    <td>${member.nick}</td>
    <td>${member.bonus}</td>
    <td>${member.member_ver_state}</td>
    <td>${member.suspend_deadline}</td>
    <td>${member.violation}</td>
    <td>
        <a class="btn btn-primary btn-sm d-none d-sm-inline-block custom-member-button toggle-button"
            role="button" >
            詳細資料
        </a>
        `
            +
            BanHtml
            +
            `
        <br style="padding-top: 3px; padding-bottom: 3px;">
        `
            +
            cancelBanHtml
            +
            foreverBanHtml
            +
            `
    </td>
</tr>
<tr class="dropdown_row" style="display:none;" id="toggle_button_${member.member_id}">
    <td colspan="6">
        <div style="display: flex; flex-wrap: wrap;">
            <div style="width: 50%;">
                <span class="pwd-span">密碼：****</span>
                <button class="btn btn-primary btn-sm d-none d-sm-inline-block show-password"
                    data-password="${member.password}">顯示</button>
            </div>
            <div style="width: 50%;">電話：${member.phone}</div>
            <div style="width: 50%;">信箱：${member.email}</div>
            <div style="width: 50%;">生日：${member.birth}</div>
            <div style="width: 50%;">身分證字號：${member.id_number}</div>
            <div style="width: 100%;">地址：${member.address}</div>
        </div>
    </td>
</tr>
      `;
    });

    // 插入生成的 HTML 內容到 memberListContainer 元素中
    memberListContainer.innerHTML = html;

    $(".toggle-button").click(function () {
        event.preventDefault();
        var dropdownRow = $(this).closest("tr").next(".dropdown_row");
        dropdownRow.slideToggle("fast");

        // 切換按鈕文字
        $(this).text(function (_, text) {
            return text.trim() === "詳細資料" ? "收回" : "詳細資料";
        });
        return false;
    });

    $(".show-password").click(function () {
        event.preventDefault();
        var showButton = $(this);
        var passwordSpan = showButton.prev(".pwd-span");
        var password = showButton.data("password")

        // 切换按钮文字和密码显示
        if (passwordSpan.text().trim() === "密碼：****") {
            showButton.text("隱藏");
            passwordSpan.text("密碼：" + password);
        } else {
            showButton.text("顯示");
            passwordSpan.text("密碼：****");
        }

        return false;
    });


}



