package gg.nbp.web.shop.shopproduct.listeners;

import gg.nbp.web.shop.shopproduct.util.ConstUtil;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

@WebListener
public class ShopProductListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ClassPathResource resource = new ClassPathResource("");
            File file = resource.getFile();
            String classPath = file.getAbsolutePath();
            int targetIndex = classPath.lastIndexOf("target");
            String projectPath = classPath.substring(0, targetIndex - 1);
            ConstUtil.PATH = projectPath;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}

