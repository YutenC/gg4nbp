package gg.nbp.web.Member.util;

import com.google.gson.Gson;

import gg.nbp.web.Member.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;

public class MemberCommonUitl {

    // 因為前端用ajax接收JSON物件，所以只要將重複的gson轉換json物件並回應的方法，拉出來寫一次就可以重複用
    public static <E> void gsonToJson(HttpServletResponse response, E entity) {
        try {
            // 創建一個Gson物件
            Gson gson = new Gson();
            // 將傳入的實體用gson的方法轉為json物件
            String gsonData = gson.toJson(entity);
            response.getWriter().write(gsonData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Member getMemberSession(HttpServletRequest request, String key){
       return  (Member)request.getSession().getAttribute(key);
    }

    public static Member visitorData(Member member){

        Member visitor = new Member();
        visitor.setMember_id(member.getMember_id());
        visitor.setAccount(member.getAccount());
        visitor.setNick(member.getNick());
        visitor.setPhone(member.getPhone());
        visitor.setBonus(member.getBonus());
        visitor.setBirth(member.getBirth());
        visitor.setId_number(member.getId_number());
        visitor.setPhone(member.getPhone());
        visitor.setAddress(member.getAddress());
        visitor.setEmail(member.getEmail());
        visitor.setHeadshot(member.getHeadshot());
        visitor.setMember_ver_state(member.getMember_ver_state());
        visitor.setMessage(member.getMessage());
        visitor.setSuccessful(member.isSuccessful());

        return visitor;
    }

    public static String verificationCode(){
        String code = "zxcvbnmasdfghjklqwertyuiopZXCVBNMASDFGHJKLQWERTYUIOP1234567890";
        String verificationCode = "";
        for (int i = 0; i <= 8; i++) {
            verificationCode += code.charAt((int)(Math.random() * 62));
        }
        return verificationCode;
    }

    public static String getAbsolutePath(String relativePath) {
        File file = new File(relativePath);
        return file.getAbsolutePath();
    }
}
