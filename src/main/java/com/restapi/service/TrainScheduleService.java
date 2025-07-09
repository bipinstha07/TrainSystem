package com.restapi.service;

import com.restapi.dto.TrainRouteDto;
import com.restapi.dto.TrainScheduleDto;

import java.util.List;

public interface TrainScheduleService {
    TrainScheduleDto createSchedule(TrainScheduleDto trainScheduleDto);

    List<TrainScheduleDto> getTrainScheduleByTrainId(Long trainId);
    void deleteTrainSchedule(Long trainScheduleId);
    TrainScheduleDto update(Long trainscheduleId, TrainScheduleDto trainScheduleDto);

    List<TrainScheduleDto> getAll();
}
