package com.restapi.service;

import com.restapi.aspectOriented.CustomPaymentAnnotation;
import com.restapi.dto.PaymentDto;
import com.restapi.entity.Booking;
import com.restapi.entity.Payment;
import com.restapi.exceptionhandler.ResourceNotFoundException;
import com.restapi.repository.BookingRepo;
import com.restapi.repository.PaymentRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PaymentServiceImp implements PaymentService{

    private PaymentRepo paymentRepo;
    private ModelMapper modelMapper;
    private BookingRepo bookingRepo;

    @Transactional
    @CustomPaymentAnnotation
    @Override
    public PaymentDto add(PaymentDto paymentDto){
        Booking booking = bookingRepo.findById(paymentDto.getBookingId()).orElseThrow(()->new ResourceNotFoundException("No Booking Found"));
        Payment payment =  modelMapper.map(paymentDto,Payment.class);
        payment.setBooking(booking);
        payment.setCreatedAt(LocalDateTime.now());
        Payment savedPayment = paymentRepo.save(payment);
        return modelMapper.map(savedPayment, PaymentDto.class);
    }

    @Override
    public PaymentDto getByTransId(String tranId){
       Payment payment= paymentRepo.findByTransactionId(tranId);
       return modelMapper.map(payment,PaymentDto.class);
    }


}
