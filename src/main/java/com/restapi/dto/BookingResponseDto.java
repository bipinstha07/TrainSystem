package com.restapi.dto;

import com.restapi.entity.BookingStatus;
import com.restapi.entity.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class BookingResponseDto {

    private Long bookingId;
    private String pnr;
    private BigDecimal totalFare;
    private BookingStatus bookingStatus;
    private List<BookingPassengerDto> passengers;
    private LocalDate journeyDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;

    private StationDto sourceStation;
    private StationDto destinationStation;
    private PaymentStatus paymentStatus;


}
