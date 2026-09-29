
package com.ecom.service.address;

import com.ecom.dto.address.AddressRequest;
import com.ecom.dto.address.AddressResponse;
import com.ecom.dto.address.AddressUpdateRequest;
import com.ecom.exceptions.address.AddressNotFoundException;
import com.ecom.exceptions.address.InvalidAddressException;
import com.ecom.exceptions.auth.UserNotAuthenticatedException;
import com.ecom.models.address.Address;
import com.ecom.models.auth.User;
import com.ecom.repository.address.AddressRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }


    public AddressResponse addAddress(AddressRequest addressRequest) {

        // 1. Get authenticated user
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !(authentication.getPrincipal() instanceof User)) {

            throw new UserNotAuthenticatedException("User not authenticated");
        }

        User user = (User) authentication.getPrincipal();
        String userId = user.getId();


        // 2. Create address
        Address address = new Address();

        address.setUserId(userId);
        address.setFullName(addressRequest.getFullName());
        address.setPhone(addressRequest.getPhone());
        address.setPinCode(addressRequest.getPinCode());
        address.setCountry(addressRequest.getCountry());
        address.setCity(addressRequest.getCity());
        address.setStreetAndHouse(addressRequest.getStreetAndHouse());

        LocalDateTime now = LocalDateTime.now();

        address.setCreatedAt(now);
        address.setUpdatedAt(now);


        // 3. Save address
        Address savedAddress = addressRepository.save(address);


        // 4. Convert Address → AddressResponse
        AddressResponse addressResponse = new AddressResponse();

        addressResponse.setId(savedAddress.getId());
        addressResponse.setUserId(savedAddress.getUserId());
        addressResponse.setFullName(savedAddress.getFullName());
        addressResponse.setPhone(savedAddress.getPhone());
        addressResponse.setPinCode(savedAddress.getPinCode());
        addressResponse.setCountry(savedAddress.getCountry());
        addressResponse.setCity(savedAddress.getCity());
        addressResponse.setStreetAndHouse(savedAddress.getStreetAndHouse());
        addressResponse.setCreatedAt(savedAddress.getCreatedAt());
        addressResponse.setUpdatedAt(savedAddress.getUpdatedAt());

        return addressResponse;
    }


    public List<AddressResponse> getAddresses() {

        // 1. Get authenticated user
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !(authentication.getPrincipal() instanceof User)) {

            throw new UserNotAuthenticatedException("User not authenticated");
        }

        User user = (User) authentication.getPrincipal();
        String userId = user.getId();


        // 2. Get all addresses belonging to this user
        List<Address> addressList =
                addressRepository.findByUserId(userId);


        // 3. Create response list
        List<AddressResponse> addressResponses = new ArrayList<>();


        // 4. Convert every Address → AddressResponse
        for (Address address : addressList) {

            AddressResponse addressResponse = new AddressResponse();

            addressResponse.setId(address.getId());
            addressResponse.setUserId(address.getUserId());
            addressResponse.setFullName(address.getFullName());
            addressResponse.setPhone(address.getPhone());
            addressResponse.setPinCode(address.getPinCode());
            addressResponse.setCountry(address.getCountry());
            addressResponse.setCity(address.getCity());
            addressResponse.setStreetAndHouse(address.getStreetAndHouse());
            addressResponse.setCreatedAt(address.getCreatedAt());
            addressResponse.setUpdatedAt(address.getUpdatedAt());

            addressResponses.add(addressResponse);
        }

        return addressResponses;
    }



    public AddressResponse updateAddress(
            String addressId,
            AddressUpdateRequest addressUpdateRequest) {

        // 1. Get authenticated user
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !(authentication.getPrincipal() instanceof User)) {

            throw new UserNotAuthenticatedException(
                    "User not authenticated"
            );
        }

        User user = (User) authentication.getPrincipal();
        String userId = user.getId();


        // 2. Find the address
        Optional<Address> addressOptional =
                addressRepository.findById(addressId);

        if (addressOptional.isEmpty()) {
            throw new AddressNotFoundException(
                    "Address not found"
            );
        }

        Address address = addressOptional.get();


        // 3. Check that this address belongs to the logged-in user
        if (!address.getUserId().equals(userId)) {
            throw new AddressNotFoundException(
                    "Address not found"
            );
        }


        // 4. Update full name if provided
        if (addressUpdateRequest.getFullName() != null) {

            if (addressUpdateRequest.getFullName().isBlank()) {
                throw new InvalidAddressException(
                        "Full name cannot be empty"
                );
            }

            address.setFullName(
                    addressUpdateRequest.getFullName()
            );
        }


        // 5. Update phone if provided
        if (addressUpdateRequest.getPhone() != null) {

            if (addressUpdateRequest.getPhone().isBlank()) {
                throw new InvalidAddressException(
                        "Phone cannot be empty"
                );
            }

            address.setPhone(
                    addressUpdateRequest.getPhone()
            );
        }


        // 6. Update pin code if provided
        if (addressUpdateRequest.getPinCode() != null) {

            if (addressUpdateRequest.getPinCode().isBlank()) {
                throw new InvalidAddressException(
                        "Pin code cannot be empty"
                );
            }

            address.setPinCode(
                    addressUpdateRequest.getPinCode()
            );
        }


        // 7. Update country if provided
        if (addressUpdateRequest.getCountry() != null) {

            if (addressUpdateRequest.getCountry().isBlank()) {
                throw new InvalidAddressException(
                        "Country cannot be empty"
                );
            }

            address.setCountry(
                    addressUpdateRequest.getCountry()
            );
        }


        // 8. Update city if provided
        if (addressUpdateRequest.getCity() != null) {

            if (addressUpdateRequest.getCity().isBlank()) {
                throw new InvalidAddressException(
                        "City cannot be empty"
                );
            }

            address.setCity(
                    addressUpdateRequest.getCity()
            );
        }


        // 9. Update street and house if provided
        if (addressUpdateRequest.getStreetAndHouse() != null) {

            if (addressUpdateRequest.getStreetAndHouse().isBlank()) {
                throw new InvalidAddressException(
                        "Street and house cannot be empty"
                );
            }

            address.setStreetAndHouse(
                    addressUpdateRequest.getStreetAndHouse()
            );
        }


        // 10. Update timestamp
        address.setUpdatedAt(LocalDateTime.now());


        // 11. Save
        Address updatedAddress =
                addressRepository.save(address);


        // 12. Convert Address → AddressResponse
        AddressResponse addressResponse = new AddressResponse();

        addressResponse.setId(updatedAddress.getId());
        addressResponse.setUserId(updatedAddress.getUserId());
        addressResponse.setFullName(updatedAddress.getFullName());
        addressResponse.setPhone(updatedAddress.getPhone());
        addressResponse.setPinCode(updatedAddress.getPinCode());
        addressResponse.setCountry(updatedAddress.getCountry());
        addressResponse.setCity(updatedAddress.getCity());
        addressResponse.setStreetAndHouse(
                updatedAddress.getStreetAndHouse()
        );
        addressResponse.setCreatedAt(
                updatedAddress.getCreatedAt()
        );
        addressResponse.setUpdatedAt(
                updatedAddress.getUpdatedAt()
        );

        return addressResponse;
    }


}