package gg.nbp.web.shop.shopproduct.filter;


import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.shop.shopproduct.pojo.ResponseMsg;
import gg.nbp.web.shop.shopproduct.util.ConvertJson;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

//,"/shopDispatcher/login/*"
@WebFilter(urlPatterns = {"/shopDispatcher/addFollow/*"})
public class BshopLoginFilter extends HttpFilter implements Filter {


    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) {

        HttpSession session= req.getSession();
        Object isLogin = session.getAttribute("isLogin");
        if (isLogin == null) {
            loginMember(req,res,chain);
        } else {

            Member member = (Member) session.getAttribute("member");
            if(member.getMember_id()==-1){
                loginMember(req,res,chain);
            }

            try {
                String path = req.getPathInfo();

                 switch (path){
                     case "/login":

                         break;
                     default:
                         chain.doFilter(req, res);
                         break;
                 }



            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        }


    }

    private void loginMember(HttpServletRequest req, HttpServletResponse res, FilterChain chain){
        res.setCharacterEncoding("UTF-8");
        // 設定回應的格式及字碼
        res.setContentType("application/json;charset=UTF-8");
        req.getSession().setAttribute("memberLocation", req.getParameter("redirectUrl"));
        PrintWriter out = null;
        try {
            String url="http://localhost:8080/gg4nbp/member_login.html";
            ResponseMsg responseMsg=new ResponseMsg.Builder().setState("redirect").setMsg(url).build();

            String strOut=ConvertJson.toJson(responseMsg);
            out = res.getWriter();
            out.print(strOut);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
