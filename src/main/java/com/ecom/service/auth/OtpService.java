package com.ecom.service.auth;

import com.ecom.dto.auth.PendingSignup;
import com.ecom.dto.auth.SendOtpRequest;
import com.ecom.exceptions.auth.PhoneAlreadyExistsException;
import com.ecom.exceptions.auth.InvalidOtpException;
import com.ecom.models.auth.User;
import com.ecom.repository.auth.UserRepository;
import com.ecom.service.redis.RedisService;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class OtpService {

    private final RedisService redisService;
    private final UserRepository userRepository;
    private final AuthenticaService authenticaService;
    private final UserService userService;

    private static final  String COOLDOWN_PREFIX = "otp:cooldown";
    private static final String RATE_LIMIT_PREFIX = "otp:rate-limit";

    private static final Duration OTP_COOLDOWN = Duration.ofSeconds(60);
    private static  final Duration RATE_LIMIT_WINDOW = Duration.ofHours(1);

    private static final int MAX_OTP_REQUESTS = 5;

    public OtpService(
            RedisService redisService,
            UserRepository userRepository,
            AuthenticaService authenticaService,
            UserService userService) {

        this.redisService = redisService;
        this.userRepository = userRepository;
        this.authenticaService = authenticaService;
        this.userService = userService;
    }

    public void sendOtp(SendOtpRequest sendOtpRequest) {

        String phone = sendOtpRequest.getPhone();

        boolean isPresent =
                userRepository.existsByPhone(phone);

        if (isPresent) {
            throw new PhoneAlreadyExistsException(
                    "Phone already registered. Please login or use forgot password."
            );
        }

        String cooldownKey = COOLDOWN_PREFIX  +  phone;
        if (redisService.hasKey(cooldownKey)){
            throw new IllegalStateException("Please wait before requesting another OTP");
        }

        String rateLimitKey = RATE_LIMIT_PREFIX  + phone;
       Long requestCount = redisService.incrementValue(rateLimitKey);

       if (requestCount ==  1){
           redisService.setValueWithExpiry(rateLimitKey , "1" , RATE_LIMIT_WINDOW);
       }

       if (requestCount > MAX_OTP_REQUESTS){
           throw  new IllegalStateException("Too many OTP request, Please try after some time");
       }

        PendingSignup pendingSignup =
                userService.pendingSignup(sendOtpRequest);

        String key = "signup:" + phone;

        redisService.setObjectWithExpiry(
                key,
                pendingSignup,
                Duration.ofMinutes(10)
        );

        authenticaService.sendOtp(phone);
        redisService.setValueWithExpiry(
                cooldownKey, "1" , OTP_COOLDOWN
        );
    }

    public void verifyOtp(String phone, String otp) {

        authenticaService.verifyOtp(phone, otp);

        String key = "signup:" + phone;

        PendingSignup pendingSignup =
                redisService.getObject(
                        key,
                        PendingSignup.class
                );

        if (pendingSignup == null) {
            throw new InvalidOtpException(
                    "Signup session expired. Please request a new OTP."
            );
        }

         userService.createUser(pendingSignup);

        redisService.deleteValue(key);


    }
}