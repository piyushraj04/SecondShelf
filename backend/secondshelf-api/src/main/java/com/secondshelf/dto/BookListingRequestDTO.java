package com.secondshelf.dto;
import com.secondshelf.enums.BookCondition;
import com.secondshelf.enums.ListingType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class BookListingRequestDTO {
    @NotNull
    private Long bookId;

    @Positive
    @NotNull
    private BigDecimal price;

    @NotNull
    private BookCondition condition;

    @Positive
    @NotNull
    private Integer quantity;

    private String description;

    private ListingType listingType = ListingType.SELL;
}
