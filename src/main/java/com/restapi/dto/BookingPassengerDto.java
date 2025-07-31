package com.restapi.dto;

import com.restapi.entity.Booking;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingPassengerDto {
    private Long id;
    private String name;
    private Integer age;
    private String gender;
    private String seatNumber;
    private String coachId;
}
