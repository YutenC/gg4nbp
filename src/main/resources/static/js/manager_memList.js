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
                member_ver_state: member.member_ver_state,
                suspend_deadline: member.suspend_deadline,
                headshot: member.headshot,
                ver_deadline: member.ver_deadline,
                violation: member.violation
            }
            member_array[member.manager_id] = (member_array_item);

            // console.log(manager_array_item);
            // console.log(member_array);


        })


        showList();

    })
    .catch(error => {
        console.error('Error:', error);
    });

// WIP




$("a.manager_search_button").on("click", () => {
    event.preventDefault();
    filtered = true;

    let searchType = $("select.manager_search_type").val();
    let searchContent = $("input.manager_search_content").val();

    // alert(typeof searchType + searchType + "\n" + typeof searchContent + searchContent);

    filtered_array = member_array.filter((manager) => {
        if (typeof manager[searchType] === "number") {
            let searchContent_num = /^[0-9]+$/.test(searchContent) ? parseInt(searchContent) : NaN;
            if (searchContent_num === NaN) {
                alert("該欄只能搜尋數字");
            } else {
                // console.log(typeof manager[searchType]);
                // console.log(typeof searchContent);

                return manager[searchType].toString().includes(searchContent);
            }
        }
        return manager[searchType].includes(searchContent);
    });

    // console.log(filtered_array);
    showList();

})

$("a.manager_default_list_button").on("click", () => {
    event.preventDefault();
    filtered = false;
    showList();
})



function changeStateClick(id) {
    event.preventDefault();

    let confirmChangeWorkingState = confirm("確定更改" + member_array[id].account + "的在職狀況嗎?")

    // 使用 AJAX 發送請求，將 ID 值傳送到後端
    if (confirmChangeWorkingState) {
        fetch('../manager/manager_state_edit', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                manager_id: member_array[id].manager_id,
                manager_account: member_array[id].account,
                manager_password: member_array[id].password,
                manager_name: member_array[id].name,
                manager_email: member_array[id].email,
                manager_phone: member_array[id].phone,
                manager_address: member_array[id].address,
                manager_is_working: member_array[id].is_working
            })
        })
            .then(response => {
                if (response.redirected) {
                    window.location.href = response.url; // 重導至指定的 URL
                } else {
                    return response.json(); // 解析 JSON 回應
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

}

function editClick(id) {
    // 將 ID 儲存到 sessionStorage 中
    sessionStorage.setItem('id', id);

}

function removeClick(id) {
    event.preventDefault();

    let confirmRemove = confirm("確定刪除" + member_array[id].account + "嗎?");
    if (confirmRemove) {

        // 使用 AJAX 發送請求，將 ID 值傳送到後端
        fetch('../manager/manager_remove', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                manager_id: member_array[id].manager_id,
                manager_account: member_array[id].account,
                manager_password: member_array[id].password,
                manager_name: member_array[id].name,
                manager_email: member_array[id].email,
                manager_phone: member_array[id].phone,
                manager_address: member_array[id].address,
                manager_is_working: member_array[id].is_working
            })
        })
            .then(resp => resp.json())
            .then(body => {

                // console.log(body);
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

    } else {
        // console.log("R U joking?");
    }



}

function showList() {
    let showArray = member_array;
    if (filtered) {
        showArray = filtered_array;
    }

    let html = '';

    showArray.forEach(manager => {

        // const powerNameArray = ['員工管理', '會員管理', '商城管理', '二手商城管理', '檢舉單管理']
        // let powerStateHtml = '';
        // powerNameArray.forEach(powerName => {
        //     powerStateHtml +=
        //         `<img src="../svg/check-svgrepo-com.svg">` +
        //         `<span>${powerName}</span>`;
        // })
        let powerStateHtml = '';


        // pomAll_array.forEach((pom) => {
        let pfeCount = 0;
        power_array.forEach((power) => {
            if (pomAll_array.some(pom => pom.manager_id === manager.manager_id && pom.power_id === power.power_id)) {
                powerStateHtml +=
                    `<img src="../svg/check-svgrepo-com.svg">` +
                    `<span>${power.name}</span>`;
            } else {
                powerStateHtml +=
                    `<img src="../svg/clear-svgrepo-com.svg">` +
                    `<span>${power.name}</span>`;
            }
            pfeCount += 1

            if (pfeCount === 3) {
                powerStateHtml += `<br style="padding-top: 3px; padding-buttom: 3px;">`
            }
        });

        // });

        let workingStateHtml = '';
        if (manager.is_working === "在職") {
            workingStateHtml = `
    <a class="btn btn-primary btn-sm d-none d-sm-inline-block is-workingState"
        role="button"
        onclick="changeStateClick(${manager.manager_id})">
        ${manager.is_working}
    </a>`
        } else {
            workingStateHtml = `
    <a class="btn btn-primary btn-sm d-none d-sm-inline-block isNot-workingState"
        role="button"
        onclick="changeStateClick(${manager.manager_id})">
        ${manager.is_working}
    </a>`
        }


        html += `
        <tr>
          <td>${manager.manager_id}</td>
          <td>${manager.account}</td>
          <td>${manager.name}</td>
          <td>
          `
            + powerStateHtml +
            `
          </td>
          <td>
          `
            + workingStateHtml +
            `  
          </td>
          <td>
              <a class="btn btn-primary btn-sm d-none d-sm-inline-block custom-manager-button toggle-button"
                  role="button">詳細資料</a>
              <a class="btn btn-primary btn-sm d-none d-sm-inline-block custom-manager-button"
                  role="button" 
                  onclick="changeStateClick(${manager.manager_id})">
                  調整在職狀態
              </a>
              <br style="padding-top: 3px; padding-buttom: 3px;">
              <a class="btn btn-primary btn-sm d-none d-sm-inline-block custom-manager-button"
                  role="button" href="manager_edit.html"
                  onclick="editClick(${manager.manager_id})">
                  修改資料
              </a>
              <a class="btn btn-primary btn-sm d-none d-sm-inline-block custom-manager-button btn-danger"
                  role="button" 
                  onclick="removeClick(${manager.manager_id})">
                  刪除管理員
              </a>
          </td>
        </tr>
        <tr class="dropdown_row" style="display:none;" id="toggle_button_${manager.manager_id}">
            <td colspan="6">
                <div style="display: flex; flex-wrap: wrap;">
                    <div style="width: 50%;">
                        <span class="pwd-span">密碼：****</span>
                        <button class="btn btn-primary btn-sm d-none d-sm-inline-block show-password" data-password="${manager.password}")">顯示</button>
                    </div>
                    <div style="width: 50%;">電話：${manager.phone}</div>
                    <div style="width: 100%;">信箱：${manager.email}</div>
                    <div style="width: 100%;">地址：${manager.address}</div>
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



