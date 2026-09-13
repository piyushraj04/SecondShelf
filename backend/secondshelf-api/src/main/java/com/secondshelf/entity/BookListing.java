package com.secondshelf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.secondshelf.enums.BookCondition;
import com.secondshelf.enums.ListingStatus;
import com.secondshelf.enums.ListingType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "book_listings")
@Getter
@Setter
public class BookListing extends BaseEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "seller_id",nullable = false)
    @JsonIgnore
    private User seller;

    @ManyToOne
    @JoinColumn(name = "book_id",nullable = false)
    private Book book;

//    private boolean isUserRoleSeller;   //bcz listing.getUser().getRole() is used

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookCondition condition = BookCondition.ACCEPTABLE;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer quantity;

    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ListingStatus status = ListingStatus.AVAILABLE;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ListingType listingType = ListingType.SELL;

    @PositiveOrZero
    @Column(nullable = false)
    private Integer availableQuantity;

}
