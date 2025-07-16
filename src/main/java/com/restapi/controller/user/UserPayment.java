package com.restapi.controller.user;

import com.restapi.dto.PaymentDto;
import com.restapi.service.PaymentServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@AllArgsConstructor
public class UserPayment {

    private PaymentDto paymentDto;
    private PaymentServiceImp paymentServiceImp;

    @PostMapping("/payment")
    public ResponseEntity<PaymentDto> payment(@RequestBody PaymentDto paymentDto){
      PaymentDto savedPaymentDto =  paymentServiceImp.add(paymentDto);
        return new ResponseEntity<>(savedPaymentDto, HttpStatus.CREATED);

    }

}
