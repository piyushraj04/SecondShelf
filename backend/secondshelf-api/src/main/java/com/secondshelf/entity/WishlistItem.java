package com.secondshelf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
//Prevent the same listing from being added twice to the same wishlist.
@Table(name = "wishlist_items",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"wishlist_id","book_listing_id"})
} )
@Getter
@Setter
public class WishlistItem extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wishlist_id",nullable = false)
    @JsonIgnore
    private Wishlist wishlist;

    @ManyToOne
    @JoinColumn(name = "book_listing_id",nullable = false)
    private BookListing bookListing;
}
