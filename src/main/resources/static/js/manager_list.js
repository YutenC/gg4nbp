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

            // console.log(manager_array_item);
            // console.log(manager_array);


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

                console.log(pomAll_array);

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
                        console.log(power_array);

                        showList();

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

    let searchType = $("select.manager_search_type").val();
    let searchContent = $("input.manager_search_content").val();

    // alert(typeof searchType + searchType + "\n" + typeof searchContent + searchContent);

    filtered_array = manager_array.filter((manager) => {
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




function showAllInfoClick(id) {
    event.preventDefault();
    console.log(id);
    alert(manager_array[id].password +
        manager_array[id].email +
        manager_array[id].phone +
        manager_array[id].address);
}


function changeStateClick(id) {
    event.preventDefault();

    let confirmChangeWorkingState = confirm("確定更改" + manager_array[id].account + "的在職狀況嗎?")

    // 使用 AJAX 發送請求，將 ID 值傳送到後端
    if (confirmChangeWorkingState) {
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

}

function editClick(id) {
    // 將 ID 儲存到 sessionStorage 中
    sessionStorage.setItem('id', id);

}

function removeClick(id) {
    event.preventDefault();

    let confirmRemove = confirm("確定刪除" + manager_array[id].account + "嗎?");
    if (confirmRemove) {

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
    let showArray = manager_array;
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
              <a class="btn btn-primary btn-sm d-none d-sm-inline-block custom-manager-button"
                  role="button" 
                  onclick="showAllInfoClick(${manager.manager_id})">
                  詳細資料
              </a>
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
      `;
    });

    // 插入生成的 HTML 內容到 managerListContainer 元素中
    managerListContainer.innerHTML = html;

}

