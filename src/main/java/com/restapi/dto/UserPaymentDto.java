package com.restapi.dto;

import com.restapi.entity.Booking;
import com.restapi.entity.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UserPaymentDto {
    private Long id;
    private Booking booking;
    private BigDecimal amount;
    private PaymentStatus payemtnStatus;
    private String paymentMethod;
    private String transactionId;
    private LocalDateTime createdAt;

}
