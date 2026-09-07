package com.ecom.models;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String name;
    private String email;
    private String phone;
    private String password;

    private boolean emailVerified;
    private boolean phoneVerified;

    private List<String> roles;

    private boolean enabled;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}