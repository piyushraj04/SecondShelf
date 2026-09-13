package com.secondshelf.dto;

import com.secondshelf.entity.User;
import com.secondshelf.enums.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BookListingResponseDTO {

    private Long listingId;

    //Book Details
    private Long bookId;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private Language language;
    private Integer publicationYear;
    private String edition;
    private String bookDescription;
    private Category category;
    private String coverImageUrl;

    //for nested seller object  because we may need more info about seller like email or address or from when he was here
    private SellerResponseDTO seller;

    //Listing details
    private BigDecimal price;
    private BookCondition condition;
    private Integer quantity;
    private String listingDescription;
    private ListingStatus status;
    private ListingType listingType;
    private Integer availableQuantity;




}
