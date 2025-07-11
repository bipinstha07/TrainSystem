package com.restapi.service;


import com.restapi.dto.PageResponse;
import com.restapi.dto.TrainDto;
import com.restapi.entity.Station;
import com.restapi.entity.Train;
import com.restapi.exceptionhandler.ResourceNotFoundException;
import com.restapi.repository.StationRepo;
import com.restapi.repository.TrainRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrainServiceImp implements TrainService{
    private final StationRepo stationRepo;
    private TrainRepo trainRepo;
    private ModelMapper modelMapper;

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


}
