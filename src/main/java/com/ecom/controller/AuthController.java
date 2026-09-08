package com.ecom.controller;

import com.ecom.dto.RegisterRequest;
import com.ecom.dto.SendOtpRequest;
import com.ecom.models.OtpVerification;
import com.ecom.models.User;
import com.ecom.service.OtpService;
import com.ecom.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final OtpService otpService;

    public AuthController(UserService userService, OtpService otpService){
        this.userService = userService;
        this.otpService = otpService;
    }
    @PostMapping("/register")
    public ResponseEntity<String> create(@Valid  @RequestBody RegisterRequest  registerRequest){
       User saved =  userService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Account create successfully");
    }
    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody SendOtpRequest sendOtpRequest){
      String otp = otpService.sendOtp(sendOtpRequest.getPhone());
    return  ResponseEntity.ok(otp);

    }

}