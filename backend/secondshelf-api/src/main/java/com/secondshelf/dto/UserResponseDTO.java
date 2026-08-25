package com.secondshelf.dto;
import com.secondshelf.enums.Role;
import com.secondshelf.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String contactNo;
    private String email;
    private Role role;
    private UserStatus userStatus;
    private String profileImageUrl;



}
