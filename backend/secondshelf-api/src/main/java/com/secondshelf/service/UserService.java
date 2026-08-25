package com.secondshelf.service;

import com.secondshelf.dto.UserRequestDTO;
import com.secondshelf.dto.UserResponseDTO;
import com.secondshelf.entity.User;
import com.secondshelf.exception.ResourceAlreadyExistsException;
import com.secondshelf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    //@RequiredArgsConstructor--The @RequiredArgsConstructor is a Lombok annotation that automatically generates a constructor with one parameter for each final field and each field marked with @NonNull that is not initialized at the declaration site.
//    UserService(UserRepository userRepository){
//        this.userRepository = userRepository;
//    }

    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO){
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already registered");
        }
        if(userRepository.existsByContactNo(userRequestDTO.getContactNo())){
            throw new ResourceAlreadyExistsException("Contact Number already registered");
        }
        User user = new User();
        user.setFullName(userRequestDTO.getFullName());
        user.setContactNo(userRequestDTO.getContactNo());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());

        User savedUser = userRepository.save(user);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(savedUser.getId());
        userResponseDTO.setFullName(savedUser.getFullName());
        userResponseDTO.setContactNo(savedUser.getContactNo());
        userResponseDTO.setEmail(savedUser.getEmail());
        userResponseDTO.setRole(savedUser.getRole());
        userResponseDTO.setUserStatus(savedUser.getUserStatus());
        userResponseDTO.setProfileImageUrl(savedUser.getProfileImageUrl());

        return userResponseDTO;
    }
}
