package com.restapi.controller.user;

import com.restapi.dto.UserPaymentDto;
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

    private UserPaymentDto userPaymentDto;
    private PaymentServiceImp paymentServiceImp;

    @PostMapping("/payment")
    public ResponseEntity<UserPaymentDto> payment(@RequestBody UserPaymentDto userPaymentDto){
      UserPaymentDto savedUserPaymentDto =  paymentServiceImp.add(userPaymentDto);
        return new ResponseEntity<>(savedUserPaymentDto, HttpStatus.CREATED);

    }

}
