package com.restapi.controller;

import com.restapi.dto.PageResponse;
import com.restapi.dto.StationDto;
import com.restapi.service.StationServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/station")
@AllArgsConstructor
public class AdminStation {

    private StationServiceImp stationServiceImp;

    @PostMapping
    public ResponseEntity<StationDto> add(
            @RequestBody StationDto stationDto
    ){
       return new ResponseEntity<>(stationServiceImp.add(stationDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageResponse<StationDto>> getAll(
            @RequestParam(value="page",defaultValue = "1") int page,
            @RequestParam(value ="size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir
    ){
        return new ResponseEntity<>(stationServiceImp.getAll(page,size,sortBy,sortDir),HttpStatus.OK);
    }

    @GetMapping("/{stationId}")
    public ResponseEntity<StationDto> getOne(
            @PathVariable Long stationId
    ){
        return new ResponseEntity<>(stationServiceImp.getOne(stationId),HttpStatus.OK);
    }

    @DeleteMapping("/{stationId}")
    public void delete(@PathVariable Long stationId){
        stationServiceImp.delete(stationId);
    }

    @PutMapping("/{stationId}")
    public ResponseEntity<StationDto> update(@PathVariable Long stationId, @RequestBody StationDto stationDto){
       return new ResponseEntity<>(stationServiceImp.update(stationId,stationDto),HttpStatus.ACCEPTED);
    }

}
