package com.secondshelf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    @JsonIgnore
    private Order order;

    @ManyToOne
    @JoinColumn(name = "book_listing_id",nullable = false)
    private BookListing bookListing;

    @Column(nullable = false)
    private BigDecimal unitPrice; //we fetch it from bookListing

    @Column(nullable = false)
    private Integer quantity;

}
