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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/{userId}/addresses")
    public ResponseEntity<ResponseStructure<AddressResponseDTO>> addAddress(@PathVariable(name = "userId") Long userId,@Valid @RequestBody AddressRequestDTO addressRequestDTO){
        AddressResponseDTO addressResponseDTO = addressService.addAddress(userId,addressRequestDTO);
        ResponseStructure<AddressResponseDTO> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Address Successfully added to user with userId " + userId);
        response.setData(addressResponseDTO);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

}
