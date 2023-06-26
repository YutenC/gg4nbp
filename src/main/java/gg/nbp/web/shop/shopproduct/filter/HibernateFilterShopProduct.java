package gg.nbp.web.shop.shopproduct.filter;



import gg.nbp.web.shop.shopproduct.util.RedisFactory;

import jakarta.servlet.FilterChain;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/shopDispatcher/*")
public class HibernateFilterShopProduct extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) {
//        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        String contextPath=req.getContextPath();
        String servletPath=req.getServletPath();
        System.out.println("contextPath: "+contextPath);
        System.out.println("servletPath: "+servletPath);

//        if(servletPath.endsWith(".html")||servletPath.endsWith(".css")||servletPath.endsWith(".js")){
//
//        }

        try {
//            HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
            chain.doFilter(req, res);
//            transaction.commit();
//            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();

//            session = HibernateUtil.getSessionFactory().getCurrentSession();
//            session.beginTransaction();
            RedisFactory.getRedisServiceInstance().process();
            RedisFactory.clear();
//            session.getTransaction().rollback();
        } catch (Exception e) {
            e.printStackTrace();
//            session.getTransaction().rollback();
//
        }
    }
}
