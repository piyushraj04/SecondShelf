package com.secondshelf.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

    @NotBlank
    private String fullName;

    @NotBlank
    private String contactNo;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Min(8)
    private String password;

}
