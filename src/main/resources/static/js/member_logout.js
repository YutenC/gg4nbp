$("li.logout").on("click", function (){    
    $.ajax({
        url: "memberLogoutServlet",
        method: "POST",
        dataType: "json",
        success:function(){
            sessionStorage.clear();
            window.location.href="../index.html";
        }
    })
})

$(window).on("load", function () {
    $.ajax({
        url: "memberGetPictureServlet",
        method: "POST",
        dataType: "json",
        success: function (response) {
            let member = response;            
            let isPass = member.successful;
            if (isPass) {   
                if(member.headshot){
                    $("div#member_pic-1").css("background-image", `url(${member.headshot})`); 
                } else {
                    $("div#member_pic-1").css("background-image", `url(https://www.gravatar.com/avatar/1234566?size=200&d=mm)`);
                }
            } 
        },
        error:function (err, xhr, status){

        }
    })
})


