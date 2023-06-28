let ban_array = [];

let banListContainer = document.querySelector('table#ban_list tbody');

// 使用 AJAX 發送請求獲取 powerList 的資料
fetch('../manager/ban_list', {
    method: 'GET',
})
    .then(response => response.json())
    .then(data => {

        // 獲取到 managerList 的資料後，動態生成 array 內容
        data.banList.forEach(ban => {

            // 假设您从后端获取到的日期时间字符串为 '23::6月::2023 23::21::23'
            var startTimeOrigin = ban.startTime;
            var dateParts = startTimeOrigin.split(' ');
            var datePart = dateParts[0].split('::');
            var timePart = dateParts[1].split('::');
            var year = parseInt(datePart[2]);
            var month = parseInt(datePart[1]) - 1; // JavaScript中月份从0开始，因此需要减去1
            var day = parseInt(datePart[0]);
            var hour = parseInt(timePart[0]);
            var minute = parseInt(timePart[1]);
            var second = parseInt(timePart[2]);
            // 将日期时间字符串转换为 Date 对象
            var startDateTime = new Date(year, month, day, hour, minute, second);
            // 使用 toLocaleString() 函数将 Date 对象格式化为所需的日期时间格式
            var formattedDateTime = startDateTime.toLocaleString('zh-CN', {
                year: 'numeric',
                month: 'long',
                day: 'numeric',
                hour: 'numeric',
                minute: 'numeric',
                second: 'numeric'
            });


            let ban_duration = '';
            if (ban.ban_durationByDay ?? false) {
                ban_duration = ban.ban_durationByDay + "天";
            } else {
                ban_duration = '';
            }

            let ban_array_item = {
                ban_id: ban.ban_id,
                member_id: ban.member_id,
                manager_id: ban.manager_id,
                ban_reason: ban.ban_reason,
                startTime: formattedDateTime,
                ban_durationByDay: ban_duration
            }
            ban_array[ban.ban_id] = (ban_array_item);

            showList();
        })
    })
    .catch(error => {
        console.error('Error:', error);
    });


function showList() {
    let showArray = ban_array;
    // if (filtered) {
    //     showArray = filtered_array;
    // }

    showArray.sort((a, b) => b.ban_id - a.ban_id);

    let html = '';

    showArray.forEach(ban => {
        html += `
<tr>
    <td>${ban.ban_id}</td>
    <td>
        <a>${ban.member_id}</a>
    </td>
    <td>
        <a>${ban.manager_id}</a>
    </td>
    <td>${ban.ban_reason}</td>
    <td>${ban.ban_durationByDay}</td>
    <td>${ban.startTime}</td>
</tr>
  `;
    });

    // 插入生成的 HTML 內容到 banListContainer 元素中
    banListContainer.innerHTML = html;

}