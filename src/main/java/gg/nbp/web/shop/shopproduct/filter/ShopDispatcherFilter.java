package gg.nbp.web.shop.shopproduct.filter;


import gg.nbp.web.shop.shopproduct.util.RedisFactory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/shopDispatcher/*")
public class ShopDispatcherFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) {
        try {
            chain.doFilter(req, res);
            RedisFactory.getRedisServiceInstance().process();
            RedisFactory.clear();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


}
