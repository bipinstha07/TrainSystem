package com.restapi.service;

import com.restapi.dto.TrainSeatDto;
import com.restapi.entity.TrainSeat;

import java.util.List;

public interface TrainSeatService {
    TrainSeatDto add(TrainSeatDto trainSeatDto);
    List<TrainSeatDto> getAll();

    List<TrainSeatDto> getByTrainScheduleId(Long tainScheduleId);
    void delete(Long id);
    TrainSeatDto update(Long id, TrainSeatDto trainSeatDto);
    List<Integer> bookSeat(int seatToBook,Long seatId) throws IllegalAccessException;
}
