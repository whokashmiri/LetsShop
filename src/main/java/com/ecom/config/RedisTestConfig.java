package com.ecom.config;

import com.ecom.service.auth.OtpService;
import com.ecom.service.redis.RedisService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RedisTestConfig {

//    @Bean
//    CommandLineRunner redisTest(RedisService redisService){
//        return args -> {
//            redisService.setValueWithExpiry("test:key" , "Hello Redis" , Duration.ofSeconds(60));
//            String value = redisService.getValue("test:key");
//            System.out.println("Redis value " + value);
//        };
//    }


    @Bean
    CommandLineRunner redisTest(OtpService otpService) {
        return args -> {

            String phone = "+966501234567";

//            String otp = otpService.sendOtp(phone);

//            System.out.println("Generated OTP: " + otp);
        };
    }
}
