package com.restapi.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDto {
    private Long id;
    private Long bookingId;
    private BigDecimal amount;
    private String payemtnStatus;
    private String paymentMethod;
    private String transactionId;
    private LocalDateTime createdAt;

}
