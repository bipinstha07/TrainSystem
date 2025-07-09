package com.restapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Booking booking;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
     private PaymentStatus payemtnStatus;
     private String paymentMethod;
     private String transactionId;
     private LocalDateTime  createdAt;


}
