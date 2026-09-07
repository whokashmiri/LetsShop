package com.ecom.service;

import com.ecom.dto.RegisterRequest;
import com.ecom.exceptions.EmailAlreadyExistsException;
import com.ecom.exceptions.PhoneAlreadyExistsException;
import com.ecom.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }else if(userRepository.existsByPhone(registerRequest.getPhone())){
            throw new PhoneAlreadyExistsException("Phone already exists");
        }
    }
}