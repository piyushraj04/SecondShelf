package com.secondshelf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.secondshelf.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId; //this also act as order no bcz its uniques and order id should also unique but one thing for accessing order from outside we need  one separate id or somethings if it's not necessery then its fine to use as both


    @ManyToOne
    @JoinColumn(name = "buyer_id",nullable = false)
    @JsonIgnore
    private User buyer;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PENDING;

    @OneToOne(mappedBy = "order")
    private Payment payment;

    @Column(nullable = false)
    private String shippingFullName;

    @Column(nullable = false)
    private String shippingAddressLine;

    @Column(nullable = false)
    private String shippingCity;

    @Column(nullable = false)
    private String shippingState;

    @Column(nullable = false)
    private String shippingPincode;

    @Column(nullable = false)
    private String shippingContactNo;


}
