package com.restapi.dto;

import com.restapi.entity.Booking;
import jakarta.persistence.ManyToOne;

public class BookingPassengerDto {
    private Long id;
    private Booking booking;
    private String name;
    private Integer age;
    private String gender;
    private String seatNo;
}
