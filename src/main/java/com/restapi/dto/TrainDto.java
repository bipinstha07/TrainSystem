package com.restapi.dto;

import com.restapi.entity.Station;
import com.restapi.entity.TrainRoute;
import com.restapi.entity.TrainSchedule;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Train id automatically insert into database",example = "1+", requiredMode= Schema.RequiredMode.REQUIRED)
    private Long id;
    private String number;
    private String name;
    private Integer totalDistance;

    private StationDto sourceStation;
    private StationDto destinationStation;

}
