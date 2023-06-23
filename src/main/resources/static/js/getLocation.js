

var check_geolocation_enabled = function () {
    if (navigator.geolocation) {
        // 取得 geolocation
        navigator.geolocation.getCurrentPosition(function (position) {
            lng_el = position.coords.longitude;   // 經度
            lat_el = position.coords.latitude;    // 緯度
            sessionStorage.setItem("lng_el", lng_el);
            sessionStorage.setItem("lat_el", lat_el);
        }, function (error) {
            Swal.fire({
                title:"使用者不同意開啟定位服務",
                text:'ERROR(' + error.code + '): ' + error.message
            })
        }, {
            enableHighAccuracy: true,    // 預設值 false。是否要回傳較精確的位置。
            maximumAge: 0,               // 預設值為 0(毫秒)，代表設備必須回傳實際的當前位置而不能使用暫存位置
            timeout: 5000                // 代表設備能夠等待方法回傳位置的最長時間（單位是毫秒），如果在時間內還沒有回傳位置資訊的話，就會執行第二個 error 函式。參數預設值是 Infinity，代表 getCurrentPosition() 此方法在沒有可用的位置前不會有任何回覆。
        });
    }
};