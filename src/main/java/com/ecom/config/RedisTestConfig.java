package com.ecom.config;

import com.ecom.service.redis.RedisService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisTestConfig {

    @Bean
    CommandLineRunner redisTest(RedisService redisService){
        return args -> {
            redisService.setValue("test:key" , "Hello Redis");
            String value = redisService.getValue("test:key");
            System.out.println("Redis value " + value);
        };
    }
}
