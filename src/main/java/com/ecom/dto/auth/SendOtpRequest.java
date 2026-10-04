package com.ecom.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SendOtpRequest {
  private   String phone;
  private  String password;

}
