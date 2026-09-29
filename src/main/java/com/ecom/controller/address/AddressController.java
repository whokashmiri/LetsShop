package com.ecom.controller.address;

import com.ecom.dto.address.AddressRequest;
import com.ecom.dto.address.AddressResponse;
import com.ecom.service.address.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    private final AddressService addressService;
    public AddressController(AddressService addressService){
        this.addressService = addressService;
    }

    public ResponseEntity<AddressResponse> addressResponseResponseEntity(@RequestBody AddressRequest addressRequest){
      AddressResponse addressResponse =  addressService.addAddress(addressRequest);
      return ResponseEntity.ok(addressResponse);

    }


}
