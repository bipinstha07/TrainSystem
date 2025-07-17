package com.restapi.controller.user;

import com.restapi.dto.PaymentDto;
import com.restapi.service.PaymentServiceImp;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{tranId}")
    public ResponseEntity<PaymentDto> getByTranx(@PathVariable String tranId){
       PaymentDto paymentDto= paymentServiceImp.getByTransId(tranId);
       return new ResponseEntity<>(paymentDto,HttpStatus.OK);
    }

}
