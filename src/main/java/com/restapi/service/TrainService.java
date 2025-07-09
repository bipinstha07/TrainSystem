package com.restapi.service;

import com.restapi.dto.PageResponse;
import com.restapi.dto.TrainDto;

public interface TrainService {

    TrainDto add(TrainDto trainDto);


    PageResponse<TrainDto> getAll(int page, int size, String sortBy, String sortDir);

    TrainDto getOne(Long trainId);

    void delete(Long trainId);

    TrainDto update(Long trainId, TrainDto trainDto);
}
