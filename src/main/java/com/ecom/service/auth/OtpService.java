package com.ecom.service.auth;

import com.ecom.exceptions.auth.InvalidOtpException;
import com.ecom.models.auth.OtpVerification;
import com.ecom.models.auth.User;
import com.ecom.repository.auth.OtpRepository;
import com.ecom.repository.auth.UserRepository;
import com.ecom.service.redis.RedisService;
import org.springframework.stereotype.Service;

import java.lang.management.OperatingSystemMXBean;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {
    private final RedisService redisService;
    private final UserRepository userRepository;
    private final AuthenticaService authenticaService;


    public OtpService ( RedisService redisService , UserRepository userRepository , AuthenticaService authenticaService){
        this.redisService = redisService;
        this.userRepository = userRepository;
        this.authenticaService = authenticaService;
    }
   public void sendOtp(String phone){
      authenticaService.sendOtp(phone);
   }
   public void verifyOtp(String phone , String otp){
   authenticaService.verifyOtp(phone , otp);

       Optional<User> user = userRepository.findByPhone(phone);
      User existingUser = user.orElseThrow();
      existingUser.setPhoneVerified(true);
      userRepository.save(existingUser);
;



   }


}