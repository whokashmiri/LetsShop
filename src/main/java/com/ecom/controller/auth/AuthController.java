package com.ecom.controller.auth;

import com.ecom.dto.auth.LoginRequest;
import com.ecom.dto.auth.SendOtpRequest;
import com.ecom.dto.auth.VerifyOtpRequest;
import com.ecom.models.auth.User;
import com.ecom.service.JwtService;
import com.ecom.service.auth.OtpService;
import com.ecom.service.auth.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final OtpService otpService;
    private final JwtService jwtService;

    public AuthController(
            UserService userService,
            OtpService otpService,
            JwtService jwtService) {

        this.userService = userService;
        this.otpService = otpService;
        this.jwtService = jwtService;
    }

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(
            @RequestBody SendOtpRequest sendOtpRequest) {

        otpService.sendOtp(sendOtpRequest);

        return ResponseEntity.ok("OTP sent successfully");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(
            @RequestBody VerifyOtpRequest verifyOtpRequest) {

        otpService.verifyOtp(
                verifyOtpRequest.getPhone(),
                verifyOtpRequest.getOtp()
        );

        return ResponseEntity.ok("Account created successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequest loginRequest) {

        User user = userService.login(loginRequest);

        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(token);
    }
}