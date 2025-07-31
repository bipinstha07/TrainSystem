package com.restapi.service;

import com.restapi.dto.TrainSeatDto;
import com.restapi.entity.TrainSchedule;
import com.restapi.entity.TrainSeat;
import com.restapi.exceptionhandler.ResourceNotFoundException;
import com.restapi.repository.TrainScheduleRepo;
import com.restapi.repository.TrainSeatRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class TrainSeatImp implements TrainSeatService{
    private TrainSeatRepo trainSeatRepo;
    private ModelMapper modelMapper;
    private TrainScheduleRepo trainScheduleRepo;

    @Override
    public TrainSeatDto add(TrainSeatDto trainSeatDto) {
        TrainSchedule trainSchedule = trainScheduleRepo.findById(trainSeatDto.getTrainScheduleId()).orElseThrow(()-> new ResourceNotFoundException("No Train Schedule Found"));
        TrainSeat trainSeat = modelMapper.map(trainSeatDto,TrainSeat.class);
       trainSeat.setTrainSchedule(trainSchedule);
       TrainSeat trainSeat1 = trainSeatRepo.save(trainSeat);
       TrainSeatDto trainSeatDto1 = modelMapper.map(trainSeat1,TrainSeatDto.class);
        return trainSeatDto1;
    }

    @Override
    public List<TrainSeatDto> getAll() {
        List<TrainSeat> trainSeats = trainSeatRepo.findAll();
        List<TrainSeatDto> trainSeatDtos = trainSeats.stream().map(trainSeat -> modelMapper.map(trainSeat,TrainSeatDto.class)).toList();
        return trainSeatDtos;
    }

    @Override
    public List<TrainSeatDto> getByTrainScheduleId(Long tainScheduleId) {
        List<TrainSeat> trainSeat = trainSeatRepo.findByTrainScheduleId(tainScheduleId);
        return trainSeat.stream().map(trainSeat1 -> modelMapper.map(trainSeat1,TrainSeatDto.class)).toList();
    }

    @Override
    public void delete(Long id) {
        TrainSeat trainSeat = trainSeatRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("No TrainSeats Found"));
        trainSeatRepo.delete(trainSeat);
    }

    @Override
    public TrainSeatDto update(Long id, TrainSeatDto trainSeatDto) {
        TrainSeat trainSeat = trainSeatRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("No Train Seats Found"));
        TrainSchedule trainSchedule = trainScheduleRepo.findById(trainSeatDto.getTrainScheduleId()).orElseThrow(()->new ResourceNotFoundException("No TrainSchedule Found"));
        trainSeat.setAvailableSeats(trainSeatDto.getAvailableSeats());
        trainSeat.setTrainSchedule(trainSchedule);
        trainSeat.setPrice(trainSeatDto.getPrice());
        trainSeat.setCoachType(trainSeatDto.getCoachType());
        trainSeat.setTotalSeats(trainSeatDto.getTotalSeats());
        trainSeat.setSeatOrder(trainSeatDto.getSeatOrder());
        return modelMapper.map(trainSeatRepo.save(trainSeat),TrainSeatDto.class);
    }


    @Synchronized
    @Override
    @Transactional
    public List<Integer> bookSeat(int seatToBook,Long seatId) throws IllegalAccessException {
        TrainSeat trainSeat= trainSeatRepo.findById(seatId).orElseThrow(()-> new ResourceNotFoundException("No Train Container Found"));
        if(trainSeat.isSeatAvailable(seatToBook)){
            trainSeat.setAvailableSeats(trainSeat.getAvailableSeats()-seatToBook);
            List<Integer> bookedSeats = new ArrayList<>();
            for(int i=1; i<=seatToBook;i++){
                bookedSeats.add(trainSeat.getNextToAssign());
               trainSeat.setNextToAssign(trainSeat.getNextToAssign()+1);
            }
            trainSeatRepo.save(trainSeat);
            return bookedSeats;
        }
        else {
            throw new IllegalAccessException("No seats available");
        }
    }

}
