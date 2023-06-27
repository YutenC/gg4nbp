
let host_context = '';
let nowDate;
const currentDate = new Date();
const year = currentDate.getFullYear();
const month = String(currentDate.getMonth() + 1).padStart(2, '0');
const day = String(currentDate.getDate()).padStart(2, '0');
nowDate = `${year}-${month}-${day}`;

gethost_context();



function gethost_context() {
    let pathName = window.document.location.pathname;
    let locathost = window.document.location.origin;
    // pathName = '/';
    if ('/' === pathName) {
        host_context = locathost + "/";
    }
    else {
        let contextpath = pathName.split('/')[1];
        host_context = locathost + "/" + contextpath + "/";
    }

    // host_context = "http://localhost:8080/shop/";
    host_context = "http://localhost:8080/gg4nbp/";

}

export function saveDataToSessionStorage(key, content) {
    const jsonData = JSON.stringify(content)
    sessionStorage.setItem(key, encodeURIComponent(jsonData));
}

export function getDataFromSessionStorage(key) {
    // e.preventDefault();
    let content;
    // 取得資料
    for (let index = 0; index < sessionStorage.length; index++) {
        const key_ = sessionStorage.key(index);
        // const value = sessionStorage.getItem(key);
        if (key === key_) {
            content = sessionStorage.getItem(key);
            break;
        }
    }

    const obj = JSON.parse(decodeURIComponent(content));
    return obj;
}




export { host_context, nowDate };