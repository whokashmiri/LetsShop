package com.ecom.service;

import com.ecom.dto.LoginRequest;
import com.ecom.dto.RegisterRequest;
import com.ecom.exceptions.EmailAlreadyExistsException;
import com.ecom.exceptions.InvalidCredentialsException;
import com.ecom.exceptions.PhoneAlreadyExistsException;
import com.ecom.exceptions.PhoneNotVerifiedException;
import com.ecom.models.OtpVerification;
import com.ecom.models.User;
import com.ecom.repository.OtpRepository;
import com.ecom.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;
    private final OtpRepository otpRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder , OtpRepository otpRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpRepository = otpRepository;
    }

    public User register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }else if(userRepository.existsByPhone(registerRequest.getPhone())){
            throw new PhoneAlreadyExistsException("Phone already exists");
        }
        String hashedPassword =  passwordEncoder.encode(registerRequest.getPassword());
        LocalDateTime now = LocalDateTime.now();


        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(hashedPassword);
        user.setPhone(registerRequest.getPhone());
        user.setEmailVerified(false);
        user.setPhoneVerified(false);
        user.setRoles(List.of("USER"));
        user.setEnabled(true);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        return userRepository.save(user);



    }


    public User login(LoginRequest loginRequest){
        Optional<User> user = userRepository.findByPhone(loginRequest.getPhone());
      String rawPassword =   loginRequest.getPassword();

        if (user.isEmpty()){
           throw new InvalidCredentialsException("Invalid phone or password");
        }  if ( !passwordEncoder.matches(rawPassword , user.get().getPassword())){
           throw new InvalidCredentialsException("Invalid phone or password");

        }

        if (!user.get().isPhoneVerified()){
            throw new PhoneNotVerifiedException("Phone not verified");
        }
        return user.get();

    }
}