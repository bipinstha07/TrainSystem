package com.restapi.dto;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainScheduleDto {

    private Long id;
    private LocalDateTime runDate;
    private Long trainId;
    private Integer availableSeats;

}
