package com.secondshelf.dto;
import com.secondshelf.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SellerResponseDTO {
    private Long id;
    private String name;
    private String profileImageUrl;
    private UserStatus userStatus;
    private BigDecimal averageRating;
    private Integer reviewCount;
}
