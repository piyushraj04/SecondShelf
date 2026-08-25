package com.secondshelf.controller;

import com.secondshelf.dto.ResponseStructure;
import com.secondshelf.dto.UserRequestDTO;
import com.secondshelf.dto.UserResponseDTO;
import com.secondshelf.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseStructure<UserResponseDTO>> registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO){
        ResponseStructure<UserResponseDTO> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("User Registered Successfully");
        UserResponseDTO savedUser = userService.registerUser(userRequestDTO);
        response.setData(savedUser);
        return new ResponseEntity<ResponseStructure<UserResponseDTO>>(response,HttpStatus.CREATED);
    }

}
