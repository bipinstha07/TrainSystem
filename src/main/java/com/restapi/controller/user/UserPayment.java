package com.restapi.controller.user;

import com.restapi.dto.PaymentDto;
import com.restapi.service.PaymentServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/payment")
@AllArgsConstructor
public class UserPayment {
    private PaymentServiceImp paymentServiceImp;

    @Operation(
            summary = "Save Payment",
            description = "This API save Payment Details"
    )
    @ApiResponses( value={
            @ApiResponse(responseCode = "200",description = "Success"),
            @ApiResponse(responseCode = "500",description = "Internal Server Error")
    }
    )
    @PostMapping()
    public ResponseEntity<PaymentDto> payment(@RequestBody PaymentDto paymentDto){
      PaymentDto savedPaymentDto =  paymentServiceImp.add(paymentDto);
        return new ResponseEntity<>(savedPaymentDto, HttpStatus.CREATED);

    }


    @Operation(
            summary = "Get Payment Details",
            description = "This API get Payment Details by Transaction ID"
    )
    @ApiResponses( value={
            @ApiResponse(responseCode = "200",description = "Success"),
            @ApiResponse(responseCode = "500",description = "Internal Server Error")
    }
    )
    @GetMapping("/{tranId}")
    public ResponseEntity<PaymentDto> getByTranx(@PathVariable String tranId){
       PaymentDto paymentDto= paymentServiceImp.getByTransId(tranId);
       return new ResponseEntity<>(paymentDto,HttpStatus.OK);
    }


    @Operation(
            summary = "Delete Payment Details",
            description = "This API delete Payment Details by Transaction ID"
    )
    @ApiResponses( value={
            @ApiResponse(responseCode = "200",description = "Success"),
            @ApiResponse(responseCode = "500",description = "Internal Server Error")
    }
    )
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{tranId}")
    public ResponseEntity<String> deletePaymentByTran(@PathVariable String tranId){
        paymentServiceImp.deleteByTran(tranId);
        return  new ResponseEntity<>("Deletion Success",HttpStatus.OK);
    }

}
