let manager_array = [];
let filtered_array = [];
let filtered = false;
let managerListContainer = document.querySelector('table#manager_list tbody');

// 使用 AJAX 發送請求獲取 managerList 的資料

fetch('../manager/manager_list', {
    method: 'GET',
})
    .then(response => response.json())
    .then(data => {

        // 獲取到 managerList 的資料後，動態生成 array 內容
        data.managerList.forEach(manager => {
            let manager_array_item = {
                manager_id: manager.manager_id,
                account: manager.account,
                password: manager.password,
                name: manager.name,
                email: manager.email,
                phone: manager.phone,
                address: manager.address,
                is_working: manager.is_working
            }
            manager_array[manager.manager_id] = (manager_array_item);

            // console.log(manager_array_item);
            // console.log(manager_array);

            showList();
        })
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
    alert(manager_array[id].address);
}


function changeStateClick(id) {
    event.preventDefault();

    // 使用 AJAX 發送請求，將 ID 值傳送到後端
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
        html += `
    <tr>
      <td>${manager.manager_id}</td>
      <td>${manager.account}</td>
      <td class="manager-password">${manager.password}</td>
      <td>${manager.name}</td>
      <td>${manager.email}</td>
      <td> ${manager.phone}</td>
      <td>${manager.is_working}</td>
      <td>
          <a class="btn btn-primary btn-sm d-none d-sm-inline-block custom-manager-button"
              role="button" href="#"
              onclick="showAllInfoClick(${manager.manager_id})">
              詳細資料
          </a>
          <a class="btn btn-primary btn-sm d-none d-sm-inline-block custom-manager-button"
              role="button" href="#"
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
              role="button" href="#"
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