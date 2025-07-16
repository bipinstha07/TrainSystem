package com.restapi.service;

import com.restapi.dto.BookingDto;
import com.restapi.entity.Booking;
import com.restapi.entity.Station;
import com.restapi.entity.TrainSchedule;
import com.restapi.entity.User;
import com.restapi.exceptionhandler.ResourceNotFoundException;
import com.restapi.repository.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@AllArgsConstructor
public class BookingServiceImp implements BookingService{

    private BookingRepo bookingRepo;
    private ModelMapper modelMapper;
    private PaymentRepo paymentRepo;
    private StationRepo stationRepo;
    private UserRepo userRepo;
    private TrainScheduleRepo trainScheduleRepo;

    public BookingDto save(BookingDto bookingDto){
       Station sourceStation = stationRepo.findById(bookingDto.getSourceStationId()).orElseThrow(()->new ResourceNotFoundException("No Source Station Found"));
        Station destinationStation = stationRepo.findById(bookingDto.getDestinationStationId()).orElseThrow(()->new ResourceNotFoundException("No Destination Station Found"));
        User user = userRepo.findById(bookingDto.getUserId()).orElseThrow(()->new ResourceNotFoundException("No user Found"));
        TrainSchedule trainSchedule = trainScheduleRepo.findById(bookingDto.getTrainScheduleId()).orElseThrow(()->new ResourceNotFoundException("No Train Schedule"));
        bookingDto.setCreatedAt(LocalDateTime.now());
        Booking booking = modelMapper.map(bookingDto,Booking.class);
        booking.setSourceStation(sourceStation);
        booking.setDestinationStation(destinationStation);
        booking.setUser(user);
        booking.setTrainSchedule(trainSchedule);
        return modelMapper.map(bookingRepo.save(booking),BookingDto.class);

    }

}
