package com.restapi.controller;

import com.restapi.dto.UserPaymentDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@AllArgsConstructor
public class UserPayment {

    private UserPaymentDto userPaymentDto;

    private  PaymentService paymentService;

    @PostMapping("/payment")
    public ResponseEntity<UserPaymentDto> payment(@RequestBody UserPaymentDto userPaymentDto){
        UserPaymentDto savedPayment = paymentService.savePayment(userPaymentDto);
        return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);

    }

}
