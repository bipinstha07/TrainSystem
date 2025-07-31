package com.restapi.service;

import com.restapi.dto.*;
import com.restapi.entity.*;
import com.restapi.exceptionhandler.ResourceNotFoundException;
import com.restapi.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BookingServiceImp implements BookingService{

    private BookingRepo bookingRepo;
    private ModelMapper modelMapper;
    private BookingPassengerRepo bookingPassengerRepo;
    private PaymentRepo paymentRepo;
    private StationRepo stationRepo;
    private UserRepo userRepo;
    private TrainRepo trainRepo;
    private TrainSeatRepo trainSeatRepo;
    private TrainScheduleRepo trainScheduleRepo;

    @Override
    public  BookingResponseDto save(BookingRequestDto bookingRequestDto){
       User user = userRepo.findById(bookingRequestDto.getUserId()).orElseThrow(()-> new ResourceNotFoundException("No User Found"));
       TrainSchedule trainSchedule= this.trainScheduleRepo.findById(bookingRequestDto.getTrainScheduleId()).orElseThrow(()->new ResourceNotFoundException("No Train Schedule Found"));
        Station sourceStation = stationRepo.findById(bookingRequestDto.getSourceStationId()).orElseThrow(()->new ResourceNotFoundException("No Source Station Found"));
        Station destinationStation = stationRepo.findById(bookingRequestDto.getDestinationStationId()).orElseThrow(()-> new ResourceNotFoundException("No Destination Station Found"));

        List<TrainSeat> coaches = trainSchedule.getTrainSeats();
        coaches.sort((s1,s2)->s1.getSeatOrder()-s2.getSeatOrder());
        List<TrainSeat> selectedCoaches = coaches.stream().filter(coach -> coach.getCoachType()==bookingRequestDto.getCoachType()).toList();

        int totalRequestSeat = bookingRequestDto.getPassengers().size();

        TrainSeat coachToBookSeat = null;
        for(TrainSeat coach: selectedCoaches){
            if(coach.isSeatAvailable(totalRequestSeat)){
                coachToBookSeat=coach;
                break;
            }
        }

        if(coachToBookSeat==null){
            throw new ResourceNotFoundException("No seats available in this coach");
        }


//        Book Seat

        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.APPROVED);
        booking.setSourceStation(sourceStation);
        booking.setDestinationStation(destinationStation);
        booking.setTrainSchedule(trainSchedule);
        booking.setUser(user);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setJourneyDate(trainSchedule.getRunDate());
        booking.setPnr(UUID.randomUUID().toString());
        booking.setTotalFare( new BigDecimal(totalRequestSeat*coachToBookSeat.getPrice()));

//    Payment
        Payment payment = new Payment();
        payment.setAmount(booking.getTotalFare());
        payment.setPaymentStatus(PaymentStatus.Not_Paid);
        payment.setBooking(booking);

        booking.setPayment(payment);

        List<BookingPassenger> bookingPassengers = new ArrayList<>();

        for(BookingPassengerDto passenger: bookingRequestDto.getPassengers()){
           BookingPassenger bookingPassenger=  modelMapper.map(passenger,BookingPassenger.class);
            bookingPassenger.setBooking(booking);
            bookingPassenger.setTrainSeat(coachToBookSeat);
            bookingPassenger.setSeatNumber(coachToBookSeat.getNextToAssign()+"");
            coachToBookSeat.setNextToAssign(coachToBookSeat.getNextToAssign()+1);
            coachToBookSeat.setAvailableSeats(coachToBookSeat.getAvailableSeats()-1);

            bookingPassengers.add(bookingPassenger);

        }

        booking.setPassengers(bookingPassengers);
        Booking savedbooking = bookingRepo.save(booking);
        trainSeatRepo.save(coachToBookSeat);

//        Creating Response

        BookingResponseDto bookingResponseDto = new BookingResponseDto();

        bookingResponseDto.setBookingId(savedbooking.getId());
        bookingResponseDto.setPnr(savedbooking.getPnr());
        bookingResponseDto.setTotalFare(savedbooking.getTotalFare());
        bookingResponseDto.setBookingStatus(savedbooking.getBookingStatus());
        bookingResponseDto.setSourceStation(modelMapper.map(sourceStation, StationDto.class));
        bookingResponseDto.setDestinationStation(modelMapper.map(destinationStation, StationDto.class));
        bookingResponseDto.setJourneyDate(trainSchedule.getRunDate());
        bookingResponseDto.setPaymentStatus(savedbooking.getPayment().getPaymentStatus());

        bookingResponseDto.setPassengers(
                savedbooking.getPassengers().stream().map(passenger->{
                    System.out.println("Passenger");
                    BookingPassengerDto bookingPassengerDto = modelMapper.map(passenger,BookingPassengerDto.class);

//                    It was looping for mapping so tried this one
//                    It was looping because of BookingPassengerDto there was Booking Type
//                    new BookingPassengerDto();
//                    bookingPassengerDto.setName(passenger.getName());
//                    bookingPassengerDto.setAge(passenger.getAge());
//                    bookingPassengerDto.setSeatNo(passenger.getSeatNumber());
//                    bookingPassengerDto.setGender(passenger.getGender());
//                    bookingPassengerDto.setId(passenger.getId());
                    bookingPassengerDto.setCoachId(passenger.getTrainSeat().getId()+"");

                    return bookingPassengerDto;
                }).toList()
        );

        TrainRoute sourceRoute = trainSchedule.getTrain().getRoutes().stream().filter(route->route.getStation().getId().equals(sourceStation.getId())).findFirst().get();
        bookingResponseDto.setDepartureTime((sourceRoute.getDepartureTime()));
        bookingResponseDto.setArrivalTime(sourceRoute.getArrivalTime());
        return bookingResponseDto;
    }

    @Override
    public BookingDto findByPnr(String pnr) {
        Booking booking = bookingRepo.findBookingByPnr(pnr);
        if (booking == null) {
            throw new ResourceNotFoundException("No PNR Found: " + pnr);
        }
        return modelMapper.map(booking, BookingDto.class);
    }



    @Override
    public BookingDto getBooking(long bookingId){
       Booking booking= bookingRepo.findById(bookingId).orElseThrow(()-> new ResourceNotFoundException("No Booking Found"));
        return modelMapper.map(booking,BookingDto.class);
    }


    @Override
    public void delete(long bookingId){
        Booking booking= bookingRepo.findById(bookingId).orElseThrow(()->new ResourceNotFoundException("No Booking Available"));
        bookingRepo.delete(booking);

    }


}
