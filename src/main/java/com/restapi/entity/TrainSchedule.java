package com.restapi.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate runDate;

    @ManyToOne
    private Train train;

    @OneToMany(mappedBy = "trainSchedule")
    private List<TrainSeat> trainSeats;

    @OneToMany(mappedBy = "trainSchedule")
    private List<Booking> bookings;

    private Integer availableSeats;

}
