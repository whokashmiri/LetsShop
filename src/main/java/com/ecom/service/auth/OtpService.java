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


    public OtpService ( RedisService redisService , UserRepository userRepository){
        this.redisService = redisService;
        this.userRepository = userRepository;
    }
   public String sendOtp(String phone){
       Random random = new Random();
       String otp = String.valueOf(random.nextInt(100000, 1000000));
       String key =  "otp:phone:" + phone;

       redisService.setValueWithExpiry(key , otp , Duration.ofMinutes(5));
       return otp;
   }
   public void verifyOtp(String phone , String otp){
      String key = "otp:phone:" +phone;
      String storedOtp =  redisService.getValue(key);
      if (storedOtp == null ){
          throw new InvalidOtpException("Invalid or expired Otp ");
      }

      if (storedOtp.equals(otp)){
          throw new InvalidOtpException("Invalid Otp");
      }

       Optional<User> user = userRepository.findByPhone(phone);
      User existingUser = user.orElseThrow();
      existingUser.setPhoneVerified(true);
      userRepository.save(existingUser);
      redisService.deleteValue(key);



   }


}