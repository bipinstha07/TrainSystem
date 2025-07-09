package com.restapi.service;

import com.restapi.dto.TrainRouteDto;
import com.restapi.entity.Train;

import java.util.List;

public interface TrainRouteService {

    TrainRouteDto addRoute(TrainRouteDto dto);
    List<TrainRouteDto> getRoutesByTrain(Long trainId);
    TrainRouteDto update(Long id, TrainRouteDto dto);
    void delete(Long id);

}
