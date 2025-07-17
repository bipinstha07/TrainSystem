package com.restapi.service;

import com.restapi.aspectOriented.CustomPaymentAnnotation;
import com.restapi.dto.PaymentDto;
import jakarta.transaction.Transactional;

public interface PaymentService  {
    @Transactional
    @CustomPaymentAnnotation
    PaymentDto add(PaymentDto paymentDto);

    PaymentDto getByTransId(String tranId);
}
