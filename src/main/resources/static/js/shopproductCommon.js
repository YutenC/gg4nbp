
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
}


export { host_context, nowDate };