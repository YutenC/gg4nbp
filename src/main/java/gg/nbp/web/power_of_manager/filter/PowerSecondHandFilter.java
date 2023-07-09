package gg.nbp.web.power_of_manager.filter;

import java.io.IOException;

import org.springframework.core.annotation.Order;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;

@WebFilter(urlPatterns = {
		"/manager/sh_ordermanage.html",
		"/manager/sh_productmanage.html",
		"/manager/sh_productmanageAdd.html",
		"/manager/sh_productmanageEdit.html"
		})
@Order(2)
public class PowerSecondHandFilter extends HttpFilter implements Filter{
	private static final long serialVersionUID = 1L;
//	private FilterConfig filterConfig;
//	
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//		this.filterConfig= filterConfig;
//	}
//	
//	@Override
//	public void destroy() {
//		filterConfig= null;
//	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		HttpServletRequest req= (HttpServletRequest) request;
//		HttpServletResponse resp= (HttpServletResponse) response;
//		
		// get Session
//		HttpSession session= req.getSession();
		// 判斷登入
		
//		List<Power> powerList= (List<Power>) session.getAttribute("powerList");
//		Optional<Integer> powerMngIdOpt = powerList.stream()
//		        .filter(power -> "二手商城管理員".equals(power.getPower_name()))
//		        .map(Power::getPower_id)
//		        .findFirst();
//	    int powerMngId = powerMngIdOpt.get();
		
//		List<Power_of_Manager> LoggedPomList= (List<Power_of_Manager>) session.getAttribute("loggedPomList");
		
//		boolean hasPower = LoggedPomList.stream()
//		        .anyMatch(pom -> pom.getPower_id() == powerMngId);
		
//		if (!(hasPower)) {
//			String redirectUrl = req.getRequestURI();
			
			// 将需要跳转的页面路径存储在会话变量中
//			req.getSession().setAttribute("redirectUrl", redirectUrl);
			
//			resp.sendRedirect(req.getContextPath()+ "/manager_noPower.html"); 
//			return;
//		}else {
			chain.doFilter(request, response);
//		}
		
	}
}
