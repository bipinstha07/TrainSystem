package com.restapi.controller.user;

import com.restapi.dto.PaymentDto;
import com.restapi.service.PaymentServiceImp;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/payment")
@AllArgsConstructor
public class UserPayment {
    private PaymentServiceImp paymentServiceImp;

    @PostMapping()
    public ResponseEntity<PaymentDto> payment(@RequestBody PaymentDto paymentDto){
      PaymentDto savedPaymentDto =  paymentServiceImp.add(paymentDto);
        return new ResponseEntity<>(savedPaymentDto, HttpStatus.CREATED);

    }

}
