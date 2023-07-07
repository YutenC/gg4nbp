package gg.nbp.core.filter;

import java.io.IOException;

import org.springframework.core.annotation.Order;

import gg.nbp.web.Member.entity.Member;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/member/*")
@Order(1)
public class MemberFilter extends HttpFilter implements Filter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Member member = new Member();
        Object isLogin =  request.getSession().getAttribute("isLogin");

        // 存取會員當前請求的網址
        final HttpSession session = request.getSession();

        if(isLogin != null ){
            chain.doFilter(request,response);
        } else {
            session.setAttribute("memberLocation", request.getHeader("referer"));
            response.sendRedirect(request.getContextPath()+"/member_login.html");
        }
    }
}
