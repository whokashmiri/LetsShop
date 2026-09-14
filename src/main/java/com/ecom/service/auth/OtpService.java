package com.ecom.service.auth;

import com.ecom.exceptions.auth.InvalidOtpException;
import com.ecom.models.auth.OtpVerification;
import com.ecom.models.auth.User;
import com.ecom.repository.auth.OtpRepository;
import com.ecom.repository.auth.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {
    private final OtpRepository otpRepository;
    private final UserRepository userRepository;


    public OtpService (OtpRepository otpRepository , UserRepository userRepository){
        this.otpRepository = otpRepository;
        this.userRepository = userRepository;
    }
   public String sendOtp(String phone){
       Random random = new Random();
       String otp = String.valueOf(random.nextInt(100000, 1000000));
       LocalDateTime now = LocalDateTime.now();
       LocalDateTime expiresAt = now.plusMinutes(5);
       OtpVerification otpVerification = new OtpVerification();
       otpVerification.setPhone(phone);
       otpVerification.setOtp(otp);
       otpVerification.setCreatedAt(now);
       otpVerification.setExpiresAt(expiresAt);
       otpVerification.setVerified(false);

       otpRepository.save(otpVerification);
       return otp;
   }
   public void verifyOtp(String phone , String otp){
       Optional<OtpVerification> otpVerification =  otpRepository.findByPhoneAndOtp(phone , otp);

      if (otpVerification.isEmpty()){
         throw new InvalidOtpException("Invalid OTP");
      }
      if( LocalDateTime.now().isAfter(otpVerification.get().getExpiresAt())){
          throw new InvalidOtpException("Expired OTP");
       }
      if (otpVerification.get().isVerified()) {
           throw new InvalidOtpException("OTP already verified");
       }
       Optional<User> user =  userRepository.findByPhone(phone);
      User existingUser =  user.orElseThrow();
      existingUser.setPhoneVerified(true);
        otpVerification.get().setVerified(true);
        userRepository.save(existingUser);
        otpRepository.save(otpVerification.get());



   }


}