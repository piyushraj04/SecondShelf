package com.secondshelf.dto;

import com.secondshelf.entity.Book;
import com.secondshelf.enums.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BookListingSummaryDTO {

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

    //listing info
    private Long listingId;
    private BigDecimal price;
    private BookCondition condition = BookCondition.ACCEPTABLE;
    private Integer quantity;
    private String description;
    private ListingStatus status = ListingStatus.AVAILABLE;
    private ListingType listingType = ListingType.SELL;
    private Integer availableQuantity;
}
