package gg.nbp.web.shop.shopproduct.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class ACORSFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest= (HttpServletRequest) request;
        // 將ServletResponse轉換為HttpServletResponse
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String ss =httpRequest.getSession().getId();
        System.out.println(ss);
        // 設置"Access-Control-Allow-Origin"頭，允許任意來源訪問
//        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        String oo=  httpRequest.getHeader("Origin");
        httpResponse.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
//        httpResponse.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5501");

        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        // 設置"Access-Control-Allow-Methods"頭，允許GET，POST，PUT和DELETE方法
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        // 設置"Access-Control-Allow-Headers"頭，允許Content-Type頭
//        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type");
        httpResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type, Origin");
        // 如果請求的方法是OPTIONS，則設置狀態為204（無內容）
        String method = ((HttpServletRequest) request).getMethod();
        if ("OPTIONS".equals(method)) {
            httpResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }


        String cookieValue = "JSESSIONID=" + httpRequest.getSession().getId() + "; Secure; SameSite=None";
        httpResponse.setHeader("Set-Cookie", cookieValue);

        // 設定 response 的 charset 為 UTF-8
        httpResponse.setCharacterEncoding("UTF-8");
        // 設定回應的格式及字碼
        httpResponse.setContentType("application/json;charset=UTF-8");
        // 繼續過濾鏈
        chain.doFilter(request, response);
    }
}
