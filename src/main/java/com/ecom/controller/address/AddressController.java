package com.ecom.controller.address;

import com.ecom.dto.address.AddressRequest;
import com.ecom.dto.address.AddressResponse;
import com.ecom.dto.address.AddressUpdateRequest;
import com.ecom.service.address.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService){
        this.addressService = addressService;
    }
    @PostMapping
    public ResponseEntity<AddressResponse> addressResponseResponseEntity(@RequestBody AddressRequest addressRequest){
      AddressResponse addressResponse =  addressService.addAddress(addressRequest);
      return ResponseEntity.ok(addressResponse);

    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAddress(){
        List<AddressResponse> addressResponse =  addressService.getAddresses();
        return ResponseEntity.ok(addressResponse);

    }

    @PatchMapping("/{addressId}")
    public ResponseEntity<AddressResponse> updateAddress( @PathVariable String addressId,
                                                          @RequestBody AddressUpdateRequest addressUpdateRequest) {
        AddressResponse addressResponse = addressService.updateAddress( addressId, addressUpdateRequest );
        return ResponseEntity.ok(addressResponse); }


}
