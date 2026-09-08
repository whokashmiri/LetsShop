package com.ecom.service;

import com.ecom.exceptions.InvalidOtpException;
import com.ecom.models.OtpVerification;
import com.ecom.repository.OtpRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {
    private final OtpRepository otpRepository;
    public OtpService (OtpRepository otpRepository){
        this.otpRepository = otpRepository;
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
   public Optional<OtpVerification> verifyOtp(String phone , String otp){
       Optional<OtpVerification> otpVerification =  otpRepository.findByPhoneAndOtp(phone , otp);

      if (otpVerification.isEmpty()){
         throw new InvalidOtpException("Invalid OTP");

      }
      return otpVerification;

   }

}