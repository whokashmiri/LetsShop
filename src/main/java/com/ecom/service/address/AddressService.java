package com.ecom.service.address;

import com.ecom.dto.address.AddressRequest;
import com.ecom.dto.address.AddressResponse;
import com.ecom.exceptions.auth.UserNotAuthenticatedException;
import com.ecom.models.address.Address;
import com.ecom.models.auth.User;
import com.ecom.repository.address.AddressRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    public AddressService (AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

    public AddressResponse addAddress(AddressRequest addressRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !((authentication.getPrincipal()) instanceof User)){
            throw  new UserNotAuthenticatedException("User not authenticated");
        }
        User user = (User) authentication.getPrincipal();
        String userid = user.getId();
       Optional<Address> addressList = addressRepository.findByUserId(userid);

       if (addressList.isEmpty()){
           throw new RuntimeException("Add address first");
       }
       Address address = new Address();
       address.setUserId(userid);
       address.setFullName(addressRequest.getFullName());
       address.setPhone(addressRequest.getPhone());
       address.setPinCode(addressRequest.getPinCode());
       address.setCountry(addressRequest.getCountry());
       address.setCity(addressRequest.getCity());
       address.setStreetAndHouse(addressRequest.getStreetAndHouse());
       LocalDateTime now  = LocalDateTime.now();
       address.setCreatedAt(now);
       address.setUpdatedAt(now);
       addressRepository.save(address);

       AddressResponse addressResponse = new AddressResponse();

       addressResponse.setId(address.getId());
       addressResponse.setUserId(address.getUserId());
       addressResponse.setFullName(address.getFullName());
       addressResponse.setCountry(address.getCountry());
       addressResponse.setCity(address.getCity());
       addressResponse.setStreetAndHouse(address.getStreetAndHouse());

        addressResponse.setCreatedAt(address.getCreatedAt());
        addressResponse.setUpdatedAt(address.getUpdatedAt());


        return addressResponse;

    }
}
