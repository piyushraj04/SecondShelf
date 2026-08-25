package com.secondshelf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.secondshelf.enums.BookCondition;
import com.secondshelf.enums.ListingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_listings")
@Getter
@Setter
public class BookListing {
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

    @Min(0)
    @Column(nullable = false)
    private Integer quantity;

    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ListingStatus status = ListingStatus.AVAILABLE;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
