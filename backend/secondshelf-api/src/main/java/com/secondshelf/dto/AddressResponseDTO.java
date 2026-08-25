package com.secondshelf.dto;

import com.secondshelf.entity.User;
import com.secondshelf.enums.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponseDTO {
    private Long id;
    private Type type;
    private String addressLine;
    private String city;
    private String state;
    private String pincode;
    private boolean isDefault;
    private Long userId;
}
