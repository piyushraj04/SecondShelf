package com.secondshelf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.secondshelf.enums.OrderStatus;
import com.secondshelf.enums.PaymentMethod;
import com.secondshelf.enums.PaymentStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id

    private Long orderId; //this also act as order no bcz its uniques and order id should also unique but one thing for accessing order from outside we need  one separate id or somethings if it's not necessery then its fine to use as both


    @ManyToOne
    @JoinColumn(name = "buyer_id")
    @JsonIgnore
    private User buyer;

    private double totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItem;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;



}
