package com.ecom.repository;

import com.ecom.models.OtpVerification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface OtpRepository extends MongoRepository<OtpVerification , String > {
  Optional <OtpVerification> findByPhoneAndOtp(String phone , String otp);


}
