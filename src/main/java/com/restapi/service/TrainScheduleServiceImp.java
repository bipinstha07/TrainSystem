package com.restapi.service;

import com.restapi.dto.TrainRouteDto;
import com.restapi.dto.TrainScheduleDto;
import com.restapi.entity.Train;
import com.restapi.entity.TrainSchedule;
import com.restapi.exceptionhandler.ResourceNotFoundException;
import com.restapi.repository.TrainRepo;
import com.restapi.repository.TrainScheduleRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrainScheduleServiceImp implements  TrainScheduleService{
    private TrainScheduleRepo trainScheduleRepo;
    private TrainRepo trainRepo;
    private ModelMapper modelMapper;


    @Override
    public TrainScheduleDto createSchedule(TrainScheduleDto trainScheduleDto) {
        Train train = trainRepo.findById(trainScheduleDto.getTrainId()).orElseThrow(()->new ResourceNotFoundException("Train Not Found"));
        TrainSchedule trainSchedule = modelMapper.map(trainScheduleDto,TrainSchedule.class);
        trainSchedule.setTrain(train);
        TrainSchedule  savedTrainSchedule = trainScheduleRepo.save(trainSchedule);
        return modelMapper.map(savedTrainSchedule,TrainScheduleDto.class);

    }

    @Override
    public List<TrainScheduleDto> getTrainScheduleByTrainId(Long trainId) {
        trainRepo.findById(trainId).orElseThrow(()->new ResourceNotFoundException("Train Not Found"));
        List<TrainSchedule> trainSchedules = trainScheduleRepo.findByTrainId(trainId);
       return trainSchedules.stream().map(trainSchedule -> modelMapper.map(trainSchedule,TrainScheduleDto.class)).toList();
    }

    @Override
    public void deleteTrainSchedule(Long trainScheduleId) {
        TrainSchedule trainSchedule = trainScheduleRepo.findById(trainScheduleId).orElseThrow(()->new ResourceNotFoundException("Train Schedule Not Found"));
        trainScheduleRepo.delete(trainSchedule);
    }

    @Override
    public TrainScheduleDto update(Long trainscheduleId, TrainScheduleDto trainScheduleDto) {
        Train train = trainRepo.findById(trainScheduleDto.getTrainId()).orElseThrow(()->new ResourceNotFoundException("Train Not Found"));
        TrainSchedule trainSchedule = trainScheduleRepo.findById(trainscheduleId).orElseThrow(()->new ResourceNotFoundException("No Train Schedule Found"));
        trainSchedule.setRunDate(trainScheduleDto.getRunDate());
        trainSchedule.setAvailableSeats(trainScheduleDto.getAvailableSeats());
        trainSchedule.setTrain(train);
        trainScheduleRepo.save(trainSchedule);
        return modelMapper.map(trainSchedule,TrainScheduleDto.class);
    }

    @Override
    public List<TrainScheduleDto> getAll() {
     List<TrainSchedule> trainSchedules = trainScheduleRepo.findAll();
     return trainSchedules.stream().map(trainSchedule -> modelMapper.map(trainSchedule,TrainScheduleDto.class)).toList();
    }



}
