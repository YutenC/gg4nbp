package gg.nbp.web.Member.controller;


import java.io.File;
import java.io.IOException;
import java.io.Serial;

import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.service.MemberService;
import gg.nbp.web.Member.util.MemberCommonUitl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;



@WebServlet("/member/memberSetPictureServlet")
@MultipartConfig
public class MemberSetPictureServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 5938459106015723851L;
    @Autowired
    private MemberService SERVICE;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Member member = MemberCommonUitl.getMemberSession(request, "member");

        Part imgPart = request.getPart("image");
        // 獲取圖片 Part

//        String storageFolder = "src/main/resources/static/img/member/member_pic";
        String storageFolder = "src/main/resources/static/img/member/member_pic";
//      定義圖片存儲的資料夾路徑（相對於專案的位置）

        String storagePath = System.getProperty("user.dir") + "/" + storageFolder;
//      獲取目標資料夾的動態路徑（相對於專案的位置）

        String imgName = imgPart.getSubmittedFileName();
//      獲取圖片的檔名

        String absolutePath = MemberCommonUitl.getAbsolutePath(storagePath);
//      將相對路徑轉換為絕對路徑

        imgPart.write(absolutePath + File.separator + imgName);
//      將圖片儲存到目標路徑


//        String imgStorgePath = "C:\\backend\\projectForSpringBoots\\gg4nbp\\src\\main\\resources\\static\\img\\member\\member_pic";
//
//        // 確定圖片儲存的目標路徑
//
//        String imgName = imgPart.getSubmittedFileName();
//        // 獲取圖片的檔名
//
//        imgPart.write(imgStorgePath + File.separator + imgName);
//        // 將圖片儲存到目標路徑

        String imgUrl = request.getContextPath() + "/img/member/member_pic/" + imgName;
        // 設定圖片對外的url

        member.setHeadshot(imgUrl);
        SERVICE.edit(member);
        System.out.println("訊息：會員　" + member.getNick() + " 上傳圖片成功");

        MemberCommonUitl.gsonToJson(response, member);


    }
}
