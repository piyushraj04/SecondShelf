package com.secondshelf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.secondshelf.enums.PaymentMethod;
import com.secondshelf.enums.PaymentStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(nullable = false,unique = true)
    private String transactionId;

    @OneToOne
    @JoinColumn(name = "order_id",nullable = false,unique = true)
    @JsonIgnore
    private Order order;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
