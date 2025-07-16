package com.restapi.dto;

import com.restapi.entity.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentDto {
    private Long id;
    private Long booking;
    private BigDecimal amount;
    private PaymentStatus payemtnStatus;
    private String paymentMethod;
    private String transactionId;
    private LocalDateTime createdAt;

}
