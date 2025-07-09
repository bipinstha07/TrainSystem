package com.restapi.service;

import com.restapi.dto.TrainRouteDto;
import com.restapi.entity.Station;
import com.restapi.entity.Train;
import com.restapi.entity.TrainRoute;
import com.restapi.exceptionhandler.ResourceNotFoundException;
import com.restapi.repository.StationRepo;
import com.restapi.repository.TrainRepo;
import com.restapi.repository.TrainRouteRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class TrainRouteImp implements TrainRouteService{

    private  ModelMapper modelMapper;
    private TrainRouteRepo trainRouteRepo;
    private StationRepo stationRepo;
    private TrainRepo trainRepo;

    @Override
    public TrainRouteDto addRoute(TrainRouteDto dto) {
        Train train = trainRepo.findById(dto.getTrain().getId()).orElseThrow(()-> new ResourceNotFoundException("Train Not Found"));
        Station station = stationRepo.findById(dto.getStation().getId()).orElseThrow(()-> new ResourceNotFoundException("Station Not Found"));
        TrainRoute trainRoute = modelMapper.map(dto,TrainRoute.class);
        trainRoute.setTrain(train);
        trainRoute.setStation(station);
      TrainRoute savedTrainRouteEntity = trainRouteRepo.save(trainRoute);
       return modelMapper.map(savedTrainRouteEntity,TrainRouteDto.class);

    }

    @Override
    public List<TrainRouteDto> getRoutesByTrain(Long trainId) {
        Train train =this.trainRepo.findById(trainId).orElseThrow(()->new ResourceNotFoundException("No Train Found of the given ID"));
        List<TrainRoute> trainRoute = this.trainRouteRepo.findByTrainId(trainId);
        return trainRoute.stream().map(e->modelMapper.map(e,TrainRouteDto.class)).toList();
    }

    @Override
    public TrainRouteDto update(Long id, TrainRouteDto dto) {
     TrainRoute trainRouteEntity =   trainRouteRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("No TrainRoute Found"));
        Train train = trainRepo.findById(dto.getTrain().getId()).orElseThrow(()-> new ResourceNotFoundException("Train Not Found"));
        Station station = stationRepo.findById(dto.getStation().getId()).orElseThrow(()-> new ResourceNotFoundException("Station Not Found"));

        trainRouteEntity.setStation(station);
        trainRouteEntity.setTrain(train);
        trainRouteEntity.setArrivalTime(dto.getArrivalTime());
        trainRouteEntity.setDepartureTime(dto.getDepartureTime());
        trainRouteEntity.setHaltMinutes(dto.getHaltMinutes());
        trainRouteEntity.setStationOrder(dto.getStationOrder());
        trainRouteEntity.setDistanceFromSource(dto.getDistanceFromSource());
        TrainRoute savedTrainRoute = trainRouteRepo.save(trainRouteEntity);
        return modelMapper.map(savedTrainRoute,TrainRouteDto.class);
    }

    @Override
    public void delete(Long id) {
        TrainRoute trainRoute = trainRouteRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("No Train Route Exist"));
        trainRouteRepo.delete(trainRoute);

    }
}
