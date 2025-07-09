package com.restapi.dto;

import com.restapi.entity.Station;
import com.restapi.entity.TrainRoute;
import com.restapi.entity.TrainSchedule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainDto {

    private Long id;
    private String number;
    private String name;
    private Integer totalDistance;

    private StationDto sourceStation;
    private StationDto destinationStation;

}
