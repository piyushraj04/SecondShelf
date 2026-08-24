package com.secondshelf.entity;

import com.secondshelf.enums.Role;
import com.secondshelf.enums.UserStatus;
import jakarta.persistence.*;
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true) //r/s is already managed by user in Address
    //This child no longer belongs to any parent--by orphanRemoval
    private List<Address> addresses;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL ,orphanRemoval = true)//bxz if seller is nit thwere then their listing should not to be exist
    private List<BookListing> bookListings;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    @OneToMany(mappedBy = "buyer" , cascade = CascadeType.ALL)
    private List<Order> orders;
}
