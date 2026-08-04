package com.secondshelf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.secondshelf.enums.BookCondition;
import com.secondshelf.enums.ListingStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class BookListing {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "seller_id")
    @JsonIgnore
    private User seller;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

//    private boolean isUserRoleSeller;   //bcz listing.getUser().getRole() is used

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookCondition condition = BookCondition.ACCEPTABLE;

    @Column(nullable = false)
    private int quantity;

    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ListingStatus status = ListingStatus.AVAILABLE;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
