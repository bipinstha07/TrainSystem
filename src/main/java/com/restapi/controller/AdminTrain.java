package com.restapi.controller;

import com.restapi.dto.PageResponse;
import com.restapi.dto.TrainDto;
import com.restapi.service.TrainService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/train")
@AllArgsConstructor
public class AdminTrain {
    private TrainService trainService;

    @PostMapping
    public ResponseEntity<TrainDto> add(@RequestBody TrainDto trainDto){
        System.out.println(trainDto.getName());
        System.out.println(trainDto.getSourceStation());
        return new ResponseEntity<>(trainService.add(trainDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageResponse<TrainDto>> getAll(
            @RequestParam (value = "page" , defaultValue = "0") int page,
            @RequestParam (value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir
    ){
        return new ResponseEntity<>(trainService.getAll(page,size,sortBy,sortDir),HttpStatus.OK);

    }

    @GetMapping("/{trainId}")
    public ResponseEntity<TrainDto> getOne(
            @PathVariable Long trainId
    ){
        return new ResponseEntity<>(trainService.getOne(trainId),HttpStatus.OK);
    }


    @DeleteMapping("/{trainId}")
    public ResponseEntity<String> delete(
            @PathVariable Long trainId
    ){
        trainService.delete(trainId);
        return new ResponseEntity<>("Deletion Success",HttpStatus.ACCEPTED);
    }

    @PutMapping("/{trainId}")
    public ResponseEntity<TrainDto> update(
            @PathVariable Long trainId,
            @RequestBody TrainDto trainDto
    ){
        return new ResponseEntity<>(trainService.update(trainId,trainDto),HttpStatus.ACCEPTED);
    }

}
