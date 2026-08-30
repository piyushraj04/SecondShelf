package com.secondshelf.controller;

import com.secondshelf.dto.AddressRequestDTO;
import com.secondshelf.dto.AddressResponseDTO;
import com.secondshelf.dto.ResponseStructure;
import com.secondshelf.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/{userId}/addresses")
    public ResponseEntity<ResponseStructure<AddressResponseDTO>> addAddress(@PathVariable(name = "userId") Long userId, @Valid @RequestBody AddressRequestDTO addressRequestDTO) {
        AddressResponseDTO addressResponseDTO = addressService.addAddress(userId, addressRequestDTO);
        ResponseStructure<AddressResponseDTO> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Address Successfully added to user with userId " + userId);
        response.setData(addressResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/addresses")
    public ResponseEntity<ResponseStructure<List<AddressResponseDTO>>> getAllAddresses(@PathVariable(name = "userId") Long userId) {
        List<AddressResponseDTO> addresses = addressService.getAllAddresses(userId);
        ResponseStructure<List<AddressResponseDTO>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("All addresses for the user fetched successfully");
        response.setData(addresses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId}/addresses/{addressId}")
    public ResponseEntity<ResponseStructure<AddressResponseDTO>> getAddressById(@PathVariable(name = "userId") Long userId, @PathVariable(name = "addressId") Long addressId) {
        AddressResponseDTO address = addressService.getAddressById(userId, addressId);
        ResponseStructure<AddressResponseDTO> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Address fetched successfully for the given id");
        response.setData(address);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{userId}/addresses/{addressId}")
    public ResponseEntity<ResponseStructure<AddressResponseDTO>> updateAddress(@PathVariable(name = "userId") Long userId, @PathVariable(name = "addressId") Long addressId, @Valid @RequestBody AddressRequestDTO addressRequestDTO) {
        AddressResponseDTO address = addressService.updateAddress(userId, addressId, addressRequestDTO);
        ResponseStructure<AddressResponseDTO> response = new ResponseStructure<>();
        response.setMessage("Updated Successfully");
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(address);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/addresses/{addressId}")
    public ResponseEntity<ResponseStructure<Void>> deleteAddressById(
            @PathVariable Long userId,
            @PathVariable Long addressId) {

        addressService.deleteAddressById(userId, addressId);

        return ResponseEntity.noContent().build();
    }
}
