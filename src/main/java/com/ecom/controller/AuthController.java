package com.ecom.controller;

import com.ecom.dto.LoginRequest;
import com.ecom.dto.RegisterRequest;
import com.ecom.dto.SendOtpRequest;
import com.ecom.dto.VerifyOtpRequest;
import com.ecom.models.User;
import com.ecom.service.JwtService;
import com.ecom.service.OtpService;
import com.ecom.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final OtpService otpService;
    private final JwtService jwtService;


    public AuthController(UserService userService, OtpService otpService , JwtService jwtService){
        this.userService = userService;
        this.otpService = otpService;
        this.jwtService = jwtService;
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

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtpRequest verifyOtpRequest){
        otpService.verifyOtp(verifyOtpRequest.getPhone() , verifyOtpRequest.getOtp());
        return ResponseEntity.ok("OTP Verified");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
      User user =   userService.login(loginRequest);
        String token =  jwtService.generateToken(user);
        return ResponseEntity.ok(token);

    }



}