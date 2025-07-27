package com.restapi.dto;

import com.restapi.entity.CoachType;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailableTrainResponse {

    private Long trainId;
    private String trainName;
    private String trainNumber;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private Map<CoachType,Integer> seatsAvailable;
    private Map<CoachType,Double> priceByCoach;


}
