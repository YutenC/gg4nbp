package gg.nbp.web.Manager.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebFilter("/manager/*")
public class ManagerLoginFilter extends HttpFilter implements Filter{
	private FilterConfig filterConfig;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig= filterConfig;
	}
	
	@Override
	public void destroy() {
		filterConfig= null;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req= (HttpServletRequest) request;
		HttpServletResponse resp= (HttpServletResponse) response;
		
		// get Session
		HttpSession session= req.getSession();
		// 判斷登入
		
		Object account= session.getAttribute("manager");
		
//		Manager manager= (Manager)session.getAttribute("manager");
//		Object account= manager.getAccount();
		
		if (account== null) {
			session.setAttribute("location", req.getRequestURI());
			resp.sendRedirect(req.getContextPath()+ "/manager_login.html");
			return;
		}else {
			chain.doFilter(request, response);
		}
		
	}
	
}
