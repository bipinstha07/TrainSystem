package com.restapi.service;


import com.restapi.dto.AvailableTrainResponse;
import com.restapi.dto.PageResponse;
import com.restapi.dto.TrainDto;
import com.restapi.dto.UserTrainSearch;
import com.restapi.entity.*;
import com.restapi.exceptionhandler.ResourceNotFoundException;
import com.restapi.repository.StationRepo;
import com.restapi.repository.TrainRepo;
import com.restapi.repository.TrainScheduleRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;

@Service
@AllArgsConstructor
public class TrainServiceImp implements TrainService{
    private final StationRepo stationRepo;
    private TrainRepo trainRepo;
    private ModelMapper modelMapper;
    private TrainScheduleRepo trainScheduleRepo;

    @Override
    public TrainDto add(TrainDto trainDto) {
        Long sid = trainDto.getSourceStation().getId();
        Long did = trainDto.getDestinationStation().getId();

        Station sourceStation = stationRepo.findById(sid).orElseThrow(()->new ResourceNotFoundException("No Source Station Found"));
        Station destinationStation = stationRepo.findById(did).orElseThrow(()-> new ResourceNotFoundException("No Destination Staton Found "));

        Train train = modelMapper.map(trainDto,Train.class);
        train.setSourceStation(sourceStation);
        train.setDestinationStation(destinationStation);
        Train savedTrain =  trainRepo.save(train);
        System.out.println(trainDto.getSourceStation().getId());
        return  modelMapper.map(savedTrain,TrainDto.class);
    }

    @Override
    public PageResponse<TrainDto> getAll(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.trim().equals("asc")? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pages = PageRequest.of(page,size,sort);
        Page<Train> pageTrainEntity = trainRepo.findAll(pages);
        Page<TrainDto> pageTrainDto = pageTrainEntity.map(e->modelMapper.map(e,TrainDto.class));
        return PageResponse.fromPage(pageTrainDto);
    }

    @Override
    public TrainDto getOne(Long trainId) {
       Train train  = trainRepo.findById(trainId).orElseThrow(()-> new ResourceNotFoundException("Train Id not found"));
       return modelMapper.map(train,TrainDto.class);
    }

    @Override
    public void delete(Long trainId) {
        Train train  = trainRepo.findById(trainId).orElseThrow(()-> new ResourceNotFoundException("Train Id not found"));
        trainRepo.delete(train);
    }

    @Override
    public TrainDto update(Long trainId, TrainDto trainDto) {
        Train train  = trainRepo.findById(trainId).orElseThrow(()-> new ResourceNotFoundException("Train Id not found"));

        Long sid = trainDto.getSourceStation().getId();
        Long did = trainDto.getDestinationStation().getId();
        Station sourceStation = stationRepo.findById(sid).orElseThrow(()->new ResourceNotFoundException("No Source Station Found"));
        Station destinationStation = stationRepo.findById(did).orElseThrow(()-> new ResourceNotFoundException("No Destination Staton Found "));

        train.setName(trainDto.getName());
        train.setNumber(trainDto.getNumber());
        train.setSourceStation(sourceStation);
        train.setDestinationStation(destinationStation);
        train.setTotalDistance(trainDto.getTotalDistance());
        Train savedTrain = trainRepo.save(train);
        return  modelMapper.map(savedTrain,TrainDto.class);
    }

    @Override
    public List<AvailableTrainResponse> userSearchTrain(UserTrainSearch userTrainSearch) {
            List<Train> matchedTrains = trainRepo.findTrainBySourceAndDestination(userTrainSearch.getSourceStationId(),userTrainSearch.getDestinationId());
            if(matchedTrains==null){
                return null;
            }

            List<Train> validTrain = new ArrayList<>();

            for (Train train: matchedTrains){
                Integer sourceStationOrder= null;
                Integer destinationStationOrder= null;

                for(TrainRoute trainRoute: train.getRoutes()){
                    if(Objects.equals(trainRoute.getStation().getId(), userTrainSearch.getSourceStationId())){
                        sourceStationOrder=trainRoute.getStationOrder();
                    } else if (Objects.equals(trainRoute.getStation().getId(), userTrainSearch.getDestinationId())) {
                        destinationStationOrder=trainRoute.getStationOrder();
                    }


                }

                boolean runOnThatDay = train.getSchedules().stream().anyMatch(sch-> sch.getRunDate().isEqual(userTrainSearch.getJourneyDate()));
                if(sourceStationOrder != null && destinationStationOrder != null && sourceStationOrder<destinationStationOrder && runOnThatDay){
                    validTrain.add(train);
                }
            }

            List<AvailableTrainResponse> responses = new ArrayList<>();
            for(Train train:validTrain){
                TrainSchedule trainSchedule = train.getSchedules().stream().filter(
                        sc->sc.getRunDate().equals(userTrainSearch.getJourneyDate())).findFirst()
                        .orElse(null);

                if(trainSchedule==null){
                    continue;
                }
                TrainRoute trainRoute = train.getRoutes().stream().filter(
                        route-> route.getStation().getId()==userTrainSearch.getSourceStationId()).findFirst()
                        .orElse(null);

                if(trainRoute==null){
                    continue;
                }

                Map<CoachType,Integer> seatMap = new HashMap<>();
                Map<CoachType,Double> priceMap = new HashMap<>();
                for(TrainSeat trainSeat: trainSchedule.getTrainSeats()){
                    seatMap.merge(trainSeat.getCoachType(),trainSeat.getAvailableSeats(),Integer::sum);
                    priceMap.putIfAbsent(trainSeat.getCoachType(),trainSeat.getPrice());
                }

                AvailableTrainResponse availableTrainResponse = AvailableTrainResponse.builder()
                        .trainNumber(train.getNumber())
                        .trainId(train.getId())
                        .trainName(train.getName())
                        .departureTime(trainRoute.getDepartureTime())
                        .arrivalTime(trainRoute.getArrivalTime())
                        .seatsAvailable(seatMap)
                        .priceByCoach(priceMap)
                        .trainScheduleId(trainSchedule.getId())
                        .scheduleDate(trainSchedule.getRunDate())
                        .build();

                responses.add(availableTrainResponse);
            }
        return responses;

    }

}
