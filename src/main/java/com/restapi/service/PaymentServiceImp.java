package com.restapi.service;

import com.restapi.aspectOriented.CustomPaymentAnnotation;
import com.restapi.dto.PaymentDto;
import com.restapi.entity.Payment;
import com.restapi.repository.PaymentRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentServiceImp implements PaymentService{

    private PaymentRepo paymentRepo;
    private ModelMapper modelMapper;

    @CustomPaymentAnnotation
    public PaymentDto add(PaymentDto paymentDto){
        Payment payment =  modelMapper.map(paymentDto,Payment.class);
        Payment savedPayment = paymentRepo.save(payment);
        return modelMapper.map(savedPayment, PaymentDto.class);
    }

}
