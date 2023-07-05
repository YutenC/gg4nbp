package gg.nbp.web.SecondHand.buy.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.annotation.PreDestroy;

@Component
public class DateSource {
	@Autowired
	static private HikariDataSource dataSource;


    @PreDestroy
    static public void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
