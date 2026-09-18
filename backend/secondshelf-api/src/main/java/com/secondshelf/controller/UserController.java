package com.secondshelf.controller;

import com.secondshelf.dto.ResponseStructure;
import com.secondshelf.dto.SellerResponseDTO;
import com.secondshelf.dto.UserRequestDTO;
import com.secondshelf.dto.UserResponseDTO;
import com.secondshelf.entity.User;
import com.secondshelf.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class UserController {
    private final UserService userService;

    @PostMapping("users/register")
    public ResponseEntity<ResponseStructure<UserResponseDTO>> registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO){
        ResponseStructure<UserResponseDTO> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("User Registered Successfully");
        UserResponseDTO savedUser = userService.registerUser(userRequestDTO);
        response.setData(savedUser);
        return new ResponseEntity<ResponseStructure<UserResponseDTO>>(response,HttpStatus.CREATED);
    }

    @PostMapping("sellers/register")
    public ResponseEntity<ResponseStructure<UserResponseDTO>> registerSeller(@Valid @RequestBody UserRequestDTO userRequestDTO){
        ResponseStructure<UserResponseDTO> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Seller Registered Successfully");
        UserResponseDTO savedSeller = userService.registerSeller(userRequestDTO);
        response.setData(savedSeller);
        return new ResponseEntity<ResponseStructure<UserResponseDTO>>(response,HttpStatus.CREATED);
    }

    @GetMapping("users/{userId}")
    public ResponseEntity<ResponseStructure<UserResponseDTO>> getUserById(@PathVariable(name = "userId") Long userId){
        UserResponseDTO user = userService.getUserById(userId);
        ResponseStructure<UserResponseDTO> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.FOUND.value());
        response.setMessage("User is found successfully");
        response.setData(user);
        return new ResponseEntity<>(response,HttpStatus.FOUND);
    }

    @GetMapping("sellers/{sellerId}")
    public ResponseEntity<ResponseStructure<SellerResponseDTO>> getSellerById(@PathVariable(name = "sellerId") Long sellerId){
        SellerResponseDTO seller = userService.getSellerById(sellerId);
        ResponseStructure<SellerResponseDTO> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.FOUND.value());
        response.setMessage("seller is found successfully");
        response.setData(seller);
        return new ResponseEntity<>(response,HttpStatus.FOUND);
    }



}
