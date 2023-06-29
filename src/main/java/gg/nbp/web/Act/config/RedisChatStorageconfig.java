package gg.nbp.web.Act.config;


import gg.nbp.web.Act.redis.RedisChatStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisChatStorageconfig {

    @Bean
    public RedisChatStorage redisChatStorage() {
        return new RedisChatStorage();
    }
}
