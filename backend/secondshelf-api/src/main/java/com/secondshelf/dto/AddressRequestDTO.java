package com.secondshelf.dto;

import com.secondshelf.enums.Type;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequestDTO {

    @NotBlank
    private String addressLine;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    @Size(min = 6)
    private String pincode;

    private boolean isDefault = false;

    private Type type = Type.HOME;
}
