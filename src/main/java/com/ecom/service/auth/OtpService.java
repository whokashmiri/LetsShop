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

        PendingSignup pendingSignup =
                userService.pendingSignup(sendOtpRequest);

        String key = "signup:" + phone;

        redisService.setObjectWithExpiry(
                key,
                pendingSignup,
                Duration.ofMinutes(10)
        );

        authenticaService.sendOtp(phone);
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