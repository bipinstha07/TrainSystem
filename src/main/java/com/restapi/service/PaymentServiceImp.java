package com.restapi.service;

import com.restapi.dto.UserPaymentDto;
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

    public UserPaymentDto add(UserPaymentDto userPaymentDto){
        Payment payment =  modelMapper.map(userPaymentDto,Payment.class);
        Payment savedPayment = paymentRepo.save(payment);
        return modelMapper.map(savedPayment,UserPaymentDto.class);
    }

}
