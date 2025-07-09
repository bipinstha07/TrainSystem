package com.restapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private TrainSchedule trainSchedule;

    @Enumerated(EnumType.STRING)
    private CoachType coachType;

    private Integer totalSeats;
    private Integer availableSeats;
    private Integer nextToAssign;
    private Double price;
    private Integer seatOrder;



    public boolean isContainerFull(){
        return availableSeats<=0;
    }

    public boolean isSeatAvailalbe(int seatToBook){
        return  (availableSeats-seatToBook)>=0;
    }



}
