package com.ecom.service.auth;

import com.ecom.dto.auth.LoginRequest;
import com.ecom.dto.auth.PendingSignup;
import com.ecom.dto.auth.SendOtpRequest;
import com.ecom.exceptions.auth.InvalidCredentialsException;
import com.ecom.exceptions.auth.PhoneAlreadyExistsException;
import com.ecom.exceptions.auth.PhoneNotVerifiedException;
import com.ecom.models.auth.User;
import com.ecom.repository.auth.OtpRepository;
import com.ecom.repository.auth.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpRepository otpRepository;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            OtpRepository otpRepository) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpRepository = otpRepository;
    }

    public PendingSignup pendingSignup(SendOtpRequest sendOtpRequest) {


        if (sendOtpRequest.getPhone() == null ||
                sendOtpRequest.getPhone().isBlank()) {
            throw new IllegalArgumentException("Phone is required");
        }

        if (sendOtpRequest.getPassword() == null ||
                sendOtpRequest.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }
        String hashedPassword =
                passwordEncoder.encode(sendOtpRequest.getPassword());

        PendingSignup pendingSignup = new PendingSignup();



        pendingSignup.setPhone(sendOtpRequest.getPhone());
        pendingSignup.setPassword(hashedPassword);

        return pendingSignup;
    }

    public void createUser(PendingSignup pendingSignup) {

        if (userRepository.existsByPhone(pendingSignup.getPhone())) {
            throw new PhoneAlreadyExistsException(
                    "Phone already registered. Please login or use forgot password."
            );
        }

        LocalDateTime now = LocalDateTime.now();

        User user = new User();

        user.setPhone(pendingSignup.getPhone());
        user.setPassword(pendingSignup.getPassword());

        user.setName(null);
        user.setEmail(null);

        user.setEmailVerified(false);
        user.setPhoneVerified(true);

        user.setRoles(List.of("USER"));
        user.setEnabled(true);

        user.setCreatedAt(now);
        user.setUpdatedAt(now);

       userRepository.save(user);
    }

    public User login(LoginRequest loginRequest) {

        Optional<User> user =
                userRepository.findByPhone(loginRequest.getPhone());

        String rawPassword = loginRequest.getPassword();

        if (user.isEmpty()) {
            throw new InvalidCredentialsException(
                    "Invalid phone or password"
            );
        }

        if (!passwordEncoder.matches(
                rawPassword,
                user.get().getPassword())) {

            throw new InvalidCredentialsException(
                    "Invalid phone or password"
            );
        }

        if (!user.get().isPhoneVerified()) {
            throw new PhoneNotVerifiedException(
                    "Phone not verified"
            );
        }

        return user.get();
    }
}