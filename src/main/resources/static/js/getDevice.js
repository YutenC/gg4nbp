// 檢查視窗大小以分類為電腦、平板或手機
function checkDeviceType() {
    // 檢查視窗寬度
    const windowWidth = window.innerWidth;
    if (windowWidth >= 1024) {
        return '電腦';
    } else if (windowWidth >= 768) {
        return '平板';
    } else {
        return '手機';
    }
}

// 檢查作業系統
function checkOperatingSystem() {
    const platform = navigator.platform.toLowerCase();
    if (platform.includes('win')) {
        return 'Windows';
    } else if (platform.includes('mac')) {
        return 'macOS';
    } else if (platform.includes('linux')) {
        return 'Linux';
    } else if (platform.includes('iphone') || platform.includes('ipad')) {
        return 'iOS';
    } else if (platform.includes('android')) {
        return 'Android';
    } else {
        return '其他作業系統';
    }
}