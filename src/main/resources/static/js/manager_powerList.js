let power_array = [];

let managerListContainer = document.querySelector('table#power_list tbody');

// 使用 AJAX 發送請求獲取 powerList 的資料

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
            power_array[power.power_id] = (power_array_item);

            showList();
        })
    })
    .catch(error => {
        console.error('Error:', error);
    });


function showList() {
    let showArray = power_array;
    // if (filtered) {
    //     showArray = filtered_array;
    // }

    let html = '';

    showArray.forEach(power => {
        html += `
<tr>
    <td>${power.power_id}</td>
    <td>${power.name}</td>
    <td>${power.content}</td>
</tr>
  `;
    });

    // 插入生成的 HTML 內容到 managerListContainer 元素中
    managerListContainer.innerHTML = html;

}