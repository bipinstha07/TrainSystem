package com.restapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;


    @ManyToOne
    private TrainSchedule trainSchedule;

    @ManyToOne
    private Station sourceStation;

    @ManyToOne
    private Station destinationStation;

    private String pnr;
    private LocalDate journeyDate;
    private BigDecimal totalFare;


    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "booking")
    private List<BookingPassenger> passengers;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payement;

}
