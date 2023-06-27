const wrapper = document.querySelector('div#wrapper');
const content = document.querySelector('div#content');
let manager_name;

document.addEventListener("DOMContentLoaded", () => {
    let side_nav = document.createElement(`nav`);
    side_nav.classList.add(`navbar`, `navbar-dark`, `align-items-start`, `sidebar`, `sidebar-dark`, `accordion`, `bg-gradient-primary`, `p-0`)
    side_nav.innerHTML = `
<div class="container-fluid d-flex flex-column p-0">
<a class="navbar-brand d-flex justify-content-center align-items-center sidebar-brand m-0"
    href="backend_homepage.html">
    <div class="sidebar-brand-icon ">
        <img class="" src="../img/Logo-removebg.png">
    </div>
    <div class="sidebar-brand-text mx-3">
        <span>NBP.GG管理</span>
    </div>
</a>
<hr class="sidebar-divider my-0">
<ul class="navbar-nav text-light" id="accordionSidebar">
    <li class="nav-item">
        <div class="x-dropdown dropdown">
            <div class="text-start x-drop-btn" aria-expanded="false" data-bs-toggle="dropdown">
                <span>管理員管理</span>
                <i class="material-icons">keyboard_arrow_down</i>
            </div>
            <div class="dropdown-menu" role="menu">
                <a class="dropdown-item" role="presentation" href="manager_list.html">管理員一覽</a>
                <a class="dropdown-item" role="presentation" href="manager_powerList.html">權限一覽</a>
            </div>
        </div>
    </li>
    <li class="nav-item">
        <div class="x-dropdown dropdown">
            <div class="text-start x-drop-btn" aria-expanded="false" data-bs-toggle="dropdown">
                <span>會員管理</span>
                <i class="material-icons">keyboard_arrow_down</i>
            </div>
            <div class="dropdown-menu" role="menu">
                <a class="dropdown-item" role="presentation" href="manager_memList.html">會員一覽</a>
                <a class="dropdown-item" role="presentation" href="manager_banList.html">停權紀錄</a>
            </div>
        </div>
    </li>
    <li class="nav-item">
        <div class="x-dropdown dropdown">
            <div class="text-start x-drop-btn" aria-expanded="false" data-bs-toggle="dropdown">
                <span>商城管理</span>
                <i class="material-icons">keyboard_arrow_down</i>
            </div>
            <div class="dropdown-menu" role="menu">
                <a class="dropdown-item" role="presentation" href="productManager.html">商品管理</a>
                <a class="dropdown-item" role="presentation" href="orderManageIndex.html">訂單一覽</a>
                <a class="dropdown-item" role="presentation" href="couponManager.html">優惠券設定</a>
            </div>
        </div>
    </li>
    <li class="nav-item">
        <div class="x-dropdown dropdown">
            <div class="text-start x-drop-btn" aria-expanded="false" data-bs-toggle="dropdown">
                <span>二手商城管理</span>
                <i class="material-icons">keyboard_arrow_down</i>
            </div>
            <div class="dropdown-menu" role="menu">
                <a class="dropdown-item" role="presentation" href="#">商品管理</a>
                <a class="dropdown-item" role="presentation" href="#">收購管理</a>
                <a class="dropdown-item" role="presentation" href="#">上架設定</a>
                <a class="dropdown-item" role="presentation" href="#">訂單一覽</a>
            </div>
        </div>
    </li>
    <li class="nav-item">
        <div class="x-dropdown dropdown">
            <div class="text-start x-drop-btn" aria-expanded="false" data-bs-toggle="dropdown">
                <span>檢舉管理</span>
                <i class="material-icons">keyboard_arrow_down</i>
            </div>
            <div class="dropdown-menu" role="menu">
                <a class="dropdown-item" role="presentation" href="#">文章檢舉一覽</a>
                <a class="dropdown-item" role="presentation" href="#">活動檢舉一覽</a>
            </div>
        </div>
    </li>
</ul>
</div>
`;

    let top_nav = document.createElement(`nav`)
    top_nav.classList.add(`navbar`, `navbar-light`, `navbar-expand`, `bg-white`, `shadow`, `mb-4`, `topbar`, `static-top`);
    top_nav.innerHTML = `
<div class="container-fluid">
<button class="btn btn-link d-md-none rounded-circle me-3" id="sidebarToggleTop" type="button">
    <i class="fas fa-bars"></i>
</button>
<h3 class="navbar-nav flex-nowrap text-dark mb-0 nav-bar-top">NBP.GG管理系統</h3>
<ul class="navbar-nav flex-nowrap ms-auto nav-bar-top">
    <li class="nav-item dropdown no-arrow">
        <div class="nav-item dropdown no-arrow">
            <span id="manaName"></span>
            <a class="btn btn-primary btn-sm d-none d-sm-inline-block btn-danger btn_log_out" >
                <i class="fas fa-sign-out-alt fa-sm fa-fw me-2 text-gray-400"></i>
                &nbsp;Logout
            </a>
        </div>
    </li>
</ul>
</div>
`;

    wrapper.insertBefore(side_nav, wrapper.firstChild);
    content.insertBefore(top_nav, content.firstChild);

    $("a.btn_log_out").on("click", () => {
        event.preventDefault;

        fetch('../manager/logout')
            .then(resp => resp.json())
            .then(body => {

                console.log(body);
                const { successful } = body;

                if (successful) {

                    alert("登出成功");
                    location.reload();
                }
            });

    })

    fetch('../manager/getName')
        .then(resp => resp.json())
        .then(body => {

            let { loggedManager_name } = body;
            manager_name = loggedManager_name || '未登入';
            manager_name = "你好，管理員：" + manager_name;
            $('#manaName').text(manager_name);


        })

})














