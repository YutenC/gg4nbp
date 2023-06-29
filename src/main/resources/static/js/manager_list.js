
let manager_array = [];
let filtered_array = [];
let filtered = false;
let managerListContainer = document.querySelector('table#manager_list tbody');

let pomAll_array = [];
let pom_array = [];
let power_array = [];


// 使用 AJAX 發送請求獲取 managerList 的資料

fetch('../manager/manager_list', {
    method: 'GET',
})
    .then(response => response.json())
    .then(data => {

        // 獲取到 managerList 的資料後，動態生成 array 內容
        data.managerList.forEach(manager => {
            let manager_workingState;
            if (manager.is_working === 1) {
                manager_workingState = "在職";
            } else {
                manager_workingState = "離職";
            }

            let manager_array_item = {
                manager_id: manager.manager_id,
                account: manager.account,
                password: manager.password,
                name: manager.name,
                email: manager.email,
                phone: manager.phone,
                address: manager.address,
                is_working: manager_workingState
            }
            manager_array[manager.manager_id] = (manager_array_item);

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

                        power_array.sort((a, b) => a.power_id - b.power_id);

                        showList();

                        manager_array.forEach(manager => {
                            manager.power_id = [0];
                            pomAll_array.forEach(pom => {
                                if (pom.manager_id === manager.manager_id) {
                                    if (!manager.power_id) {
                                        manager.power_id.push(pom.power_id);
                                    } else {
                                        manager.power_id.push(pom.power_id);
                                    }
                                }
                            })
                        })

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


$("a.manager_search_button").on("click", () => {
    event.preventDefault();
    filtered = true;

    // radios篩選
    const radios = document.getElementsByName('status');
    let stateValue;
    radios.forEach(radio => {
        if (radio.checked) {
            stateValue = radio.value;
        }
    });

    if (stateValue === "all") {
        filtered_array = manager_array;
    } else if (stateValue === "working") {
        filtered_array = manager_array.filter((manager) => {
            return manager.is_working === "在職";
        });
    } else {
        filtered_array = manager_array.filter((manager) => {
            return manager.is_working === "離職";
        });
    }

    // checkedbox篩選
    const selectAllCheckbox = document.querySelector('#selectAllCheckbox');
    const permissionAdminCheckbox = document.querySelector('#permissionAdminCheckbox');
    const memberAdminCheckbox = document.querySelector('#memberAdminCheckbox');
    const storeAdminCheckbox = document.querySelector('#storeAdminCheckbox');
    const secondhandStoreAdminCheckbox = document.querySelector('#secondhandStoreAdminCheckbox');
    const reportAdminCheckbox = document.querySelector('#reportAdminCheckbox');

    let selectedPowerIds = [];

    if (!(selectAllCheckbox.checked)) {
        if (permissionAdminCheckbox.checked) {
            selectedPowerIds.push(1);
        }

        if (memberAdminCheckbox.checked) {
            selectedPowerIds.push(2);
        }

        if (storeAdminCheckbox.checked) {
            selectedPowerIds.push(3);
        }

        if (secondhandStoreAdminCheckbox.checked) {
            selectedPowerIds.push(4);
        }

        if (reportAdminCheckbox.checked) {
            selectedPowerIds.push(5);
        }

        filtered_array = filtered_array.filter(manager => {
            return selectedPowerIds.every(powerId => manager.power_id.includes(powerId));
        });
    }


    // 文字框篩選
    let searchType = $("select.manager_search_type").val();
    let searchContent = $("input.manager_search_content").val();



    filtered_array = filtered_array.filter((manager) => {
        return manager[searchType].toString().includes(searchContent);
    });

    showList();
})

$("a.manager_default_list_button").on("click", () => {
    event.preventDefault();
    filtered = false;

    const radios = document.getElementsByName('status');
    radios.forEach(radio => {
        if (radio.value === "all") {
            radio.checked = true;
        } else {
            radio.checked = false;
        }
    });

    const checkboxes = document.querySelectorAll('.power-check .form-check-input');

    checkboxes.forEach(checkbox => {
        checkbox.checked = true;
    });

    $("input.manager_search_content").val("");

    showList();
})

function changeStateClick(id) {
    event.preventDefault();

    Swal.fire({
        title: "變更在職/離職",
        text: "確定更改" + manager_array[id].account + "的在職狀況嗎?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: '是',
        cancelButtonText: '否',
    }).then((result) => {
        if (result.isConfirmed) {
            fetch('../manager/manager_state_edit', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    manager_id: manager_array[id].manager_id,
                    manager_account: manager_array[id].account,
                    manager_password: manager_array[id].password,
                    manager_name: manager_array[id].name,
                    manager_email: manager_array[id].email,
                    manager_phone: manager_array[id].phone,
                    manager_address: manager_array[id].address,
                    manager_is_working: manager_array[id].is_working
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
    });

}

function editClick(id) {
    // 將 ID 儲存到 sessionStorage 中
    sessionStorage.setItem('id', id);
}

function removeClick(id) {
    event.preventDefault();

    Swal.fire({
        title: "刪除管理員",
        text: "確定刪除" + manager_array[id].account + "嗎?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: '是',
        cancelButtonText: '否'
    }).then((result) => {
        if (result.isConfirmed) {
            // 使用 AJAX 發送請求，將 ID 值傳送到後端
            fetch('../manager/manager_remove', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    manager_id: manager_array[id].manager_id,
                    manager_account: manager_array[id].account,
                    manager_password: manager_array[id].password,
                    manager_name: manager_array[id].name,
                    manager_email: manager_array[id].email,
                    manager_phone: manager_array[id].phone,
                    manager_address: manager_array[id].address,
                    manager_is_working: manager_array[id].is_working
                })
            })
                .then(resp => resp.json())
                .then(body => {

                    const { successful, redirectUrl } = body;

                    if (successful) {

                        Swal.fire({
                            title: '刪除完成',
                            text: `管理員"` + manager_array[id].account + `"已經刪除`,
                            icon: 'success',
                            didClose: () => {
                                if (redirectUrl) {
                                    window.location.href = redirectUrl; // 进行重定向
                                }
                            }
                        });

                    } else {

                        Swal.fire({
                            title: '修改失敗',
                            icon: 'error'
                        });
                    }


                });
        }
    });
}

function showList() {
    let showArray = manager_array;
    if (filtered) {
        showArray = filtered_array;
    }

    let html = '';

    showArray.forEach(manager => {

        let powerStateHtml = '';

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

        if (manager.manager_id === 1) {
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
                + `<a class="btn btn-primary btn-sm d-none d-sm-inline-block is-workingState"
                role="button">
                ${manager.is_working}
            </a>`
                +
                `  
          </td>
          <td>
              
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
        } else {
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
              <a class="btn btn-primary btn-sm d-none d-sm-inline-block custom-manager-button btn-danger"
                  role="button" 
                  onclick="changeStateClick(${manager.manager_id})">
                  調整在職狀態
              </a>
              <br style="padding-top: 3px; padding-bottom: 3px;">
              <a class="btn btn-primary btn-sm d-none d-sm-inline-block custom-manager-button"
                  role="button" href="manager_edit.html"
                  onclick="editClick(${manager.manager_id})">
                  修改資料
              </a>
              `
                +
                //   `
                //   <a class="btn btn-primary btn-sm d-none d-sm-inline-block custom-manager-button btn-danger"
                //       role="button" 
                //       onclick="removeClick(${manager.manager_id})">
                //       刪除管理員
                //   </a>
                //   `
                //   +
                `
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
        }
    });

    // 插入生成的 HTML 內容到 managerListContainer 元素中
    managerListContainer.innerHTML = html;

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

function toggleAllPowerBoxes() {
    const selectAllCheckbox = document.querySelector('#selectAllCheckbox');
    const checkboxes = document.querySelectorAll('.power-check:not(#selectAllCheckbox)');

    checkboxes.forEach(checkbox => {
        checkbox.checked = selectAllCheckbox.checked;
    });
}

function handleCheckboxChange() {
    const selectAllCheckbox = document.querySelector('#selectAllCheckbox');
    const checkboxes = document.querySelectorAll('.power-check:not(#selectAllCheckbox)');

    let allChecked = true;

    checkboxes.forEach(checkbox => {
        if (!checkbox.checked) {
            allChecked = false;
        }
    });

    selectAllCheckbox.checked = allChecked;
}





