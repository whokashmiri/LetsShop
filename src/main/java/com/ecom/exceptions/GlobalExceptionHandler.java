package com.ecom.exceptions;

import com.ecom.exceptions.auth.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException invalidCredentialsException){
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(invalidCredentialsException.getMessage());

    }
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExistsException(EmailAlreadyExistsException emailAlreadyExistsException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(emailAlreadyExistsException.getMessage() );
    }

    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<String> handleInvalidOtpException(InvalidOtpException invalidOtpException){
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(invalidOtpException.getMessage());

    }

    @ExceptionHandler(PhoneNotVerifiedException.class)
    public ResponseEntity<String> handlePhoneNotVerifiedException(PhoneNotVerifiedException phoneNotVerifiedException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(phoneNotVerifiedException.getMessage());

    }

    @ExceptionHandler(PhoneAlreadyExistsException.class)
    public ResponseEntity<String> handlePhoneAlreadyExistsException(PhoneAlreadyExistsException phoneAlreadyExistsException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(phoneAlreadyExistsException.getMessage());
    }


}
