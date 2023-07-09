package gg.nbp.web.power_of_manager.filter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.core.annotation.Order;

import gg.nbp.web.power.entity.Power;
import gg.nbp.web.power_of_manager.entity.Power_of_Manager;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebFilter(urlPatterns = {
		"/manager/manager_memList.html",
		"/manager/manager_banList.html",
		"/manager/manager_banAdd.html"
		})
@Order(2)
public class PowerMemFilter extends HttpFilter implements Filter{
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
		HttpServletRequest req= (HttpServletRequest) request;
		HttpServletResponse resp= (HttpServletResponse) response;
		
		// get Session
		HttpSession session= req.getSession();
		// 判斷登入
		
		@SuppressWarnings("unchecked")
		List<Power> powerList= (List<Power>) session.getAttribute("powerList");
		Optional<Integer> powerMngIdOpt = powerList.stream()
		        .filter(power -> "會員管理員".equals(power.getPower_name()))
		        .map(Power::getPower_id)
		        .findFirst();
	    int powerMngId = powerMngIdOpt.get();
		
		@SuppressWarnings("unchecked")
		List<Power_of_Manager> LoggedPomList= (List<Power_of_Manager>) session.getAttribute("loggedPomList");
		
		boolean hasPower = LoggedPomList.stream()
		        .anyMatch(pom -> pom.getPower_id() == powerMngId);
		
		if (!(hasPower)) {
			String redirectUrl = req.getRequestURI();
			
			// 将需要跳转的页面路径存储在会话变量中
			req.getSession().setAttribute("redirectUrl", redirectUrl);
			
			resp.sendRedirect(req.getContextPath()+ "/manager_noPower.html"); 
			return;
		}else {
			chain.doFilter(request, response);
		}
		
	}
}
