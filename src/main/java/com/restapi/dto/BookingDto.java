package com.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {


        private Long id;
//        private long userId;
        private Long trainScheduleId;
        private Long sourceStationId;
        private Long destinationStationId;
//        private Long userId;

    private UserDto user;

    private String pnr;
        private LocalDate journeyDate;
        private BigDecimal totalFare;
        private String bookingStatus;
        private LocalDateTime createdAt;


}
