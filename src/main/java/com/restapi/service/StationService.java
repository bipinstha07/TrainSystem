package com.restapi.service;

import com.restapi.dto.PageResponse;
import com.restapi.dto.StationDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;

public interface StationService {
     StationDto add(StationDto stationDto);
    PageResponse<StationDto> getAll(int page, int size, String sortBy, String sortDir);

    StationDto getOne(Long stationId);

    void delete(Long stationId);

    StationDto update(Long stationId, StationDto stationDto);
}
