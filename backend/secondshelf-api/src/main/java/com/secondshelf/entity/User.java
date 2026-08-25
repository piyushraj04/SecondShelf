package com.secondshelf.entity;

import com.secondshelf.enums.Role;
import com.secondshelf.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 50, name = "full_name", nullable = false)
    private String fullName;


    @Enumerated(EnumType.STRING)
    private Role role = Role.BUYER; //by default BUYER

    @Column(unique = true, nullable = false)
    private String contactNo;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus = UserStatus.ACTIVE;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    //r/s is already managed by user in Address
    //This child no longer belongs to any parent--by orphanRemoval
    private List<Address> addresses;

    //    consider whether deleting a user should delete reviews.For a marketplace, review history can have business value.
    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    @OneToMany(mappedBy = "user")//bxz if seller is not there then their listing should not to be exist
    private List<BookListing> bookListings;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    //    Deleting the user should not casually destroy financial/order history. that's why we remove cascade from here
    @OneToMany(mappedBy = "buyer")
    private List<Order> orders;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Wishlist wishlist;
}
