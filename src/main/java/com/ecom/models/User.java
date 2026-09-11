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


    public String getId(){
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword(){
        return password;
    }
    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
    public void setPhoneVerified(boolean phoneVerified){
        this.phoneVerified = phoneVerified;
    }
    public boolean isPhoneVerified(){
        return phoneVerified;
    }

    public void setRoles(List<String> roles){
        this.roles =  roles;
    }
    public List<String> getRoles(){
        return roles;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt =  createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt){
        this.updatedAt =  updatedAt;
    }
}