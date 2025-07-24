package com.restapi.service;

import com.restapi.dto.AvailableTrainResponse;
import com.restapi.dto.PageResponse;
import com.restapi.dto.TrainDto;
import com.restapi.dto.UserTrainSearch;

import java.time.LocalDate;
import java.util.List;

public interface TrainService {

    TrainDto add(TrainDto trainDto);


    PageResponse<TrainDto> getAll(int page, int size, String sortBy, String sortDir);

    TrainDto getOne(Long trainId);

    void delete(Long trainId);

    TrainDto update(Long trainId, TrainDto trainDto);

    List<AvailableTrainResponse> userSearchTrain(UserTrainSearch userTrainSearch);
}
