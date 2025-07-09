package com.restapi.service;

import com.restapi.dto.PageResponse;
import com.restapi.dto.StationDto;
import com.restapi.entity.Station;
import com.restapi.exceptionhandler.ResourceNotFoundException;
import com.restapi.repository.StationRepo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@Service
public class StationServiceImp implements StationService{
    private ModelMapper modelMapper;
    private StationRepo stationRepo;

    @Override
    public StationDto add(StationDto stationDto) {
        Station stationEntity = modelMapper.map(stationDto,Station.class);
        Station savedStationEntity = stationRepo.save(stationEntity);
        return  modelMapper.map(savedStationEntity,StationDto.class);
    }

    @Override
    public PageResponse<StationDto> getAll(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.trim().equals("asc")? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        Pageable pages = PageRequest.of(page,size,sort);
       Page<Station> stationAll = stationRepo.findAll(pages);
       Page<StationDto> stationAllDto = stationAll.map( station -> modelMapper.map(station,StationDto.class));

       return PageResponse.fromPage(stationAllDto);


    }

    @Override
    public StationDto getOne(Long stationId) {
       Station stationEntity = stationRepo.findById(stationId).orElseThrow(()-> new ResourceNotFoundException("No station id"));
        return modelMapper.map(stationEntity,StationDto.class);
    }

    @Override
    public void delete(Long stationId) {
        Station station = stationRepo.findById(stationId).orElseThrow(()-> new ResourceNotFoundException("No station id"));
        stationRepo.delete(station);
    }

    @Override
    public StationDto update(Long stationId, StationDto stationDto) {
        Station station = stationRepo.findById(stationId).orElseThrow(()-> new ResourceNotFoundException("No station id"));
        station.setName(stationDto.getName());
        station.setCity(stationDto.getCity());
        station.setCode(stationDto.getCode());
        Station savedStation = stationRepo.save(station);
        return  modelMapper.map(savedStation,StationDto.class);
    }


}
