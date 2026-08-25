package com.secondshelf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Table(name = "wishlist_items")
@Getter
@Setter
public class WishlistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wishlist_id",nullable = false)
    @JsonIgnore
    private Wishlist wishlist;

    @ManyToOne()
    @JoinColumn(name = "book_listing_id",nullable = false)
    private BookListing bookListing;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
