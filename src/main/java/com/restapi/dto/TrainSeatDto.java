package com.restapi.dto;

import com.restapi.entity.CoachType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TrainSeatDto {

    private Long id;
    private Long trainScheduleId;
    private CoachType coachType;
    private Integer totalSeats;
    private Integer availableSeats;
    private Double price;
    private Integer nextToAssign;
    private Integer seatOrder;


}
