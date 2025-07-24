package com.restapi.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTrainSearch {
    private Long sourceStationId;
    private Long destinationId;
    private LocalDate journeyDate;

}
