package com.ecom.repository;

import com.ecom.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User ,String >{
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
