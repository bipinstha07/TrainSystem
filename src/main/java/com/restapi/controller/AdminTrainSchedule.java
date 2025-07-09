package com.restapi.controller;

import com.restapi.dto.TrainScheduleDto;
import com.restapi.service.TrainScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/train-schedule")
@AllArgsConstructor
public class AdminTrainSchedule {

    private TrainScheduleService trainScheduleService;

    @PostMapping
    public ResponseEntity<TrainScheduleDto> add(@RequestBody TrainScheduleDto trainScheduleDto){
       return new ResponseEntity<>(trainScheduleService.createSchedule(trainScheduleDto), HttpStatus.CREATED);
    }

    @GetMapping("/{trainId}")
    public ResponseEntity<List<TrainScheduleDto>> getAllByTrainId(@PathVariable Long trainId){
        return new ResponseEntity<>(trainScheduleService.getTrainScheduleByTrainId(trainId),HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<TrainScheduleDto>> getAllSchedule(){
        return new ResponseEntity<>(trainScheduleService.getAll(),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{scheduleId}")
    public ResponseEntity<String> delete(@PathVariable Long scheduleId){
        trainScheduleService.deleteTrainSchedule(scheduleId);
        return new ResponseEntity<>("Deletion Success",HttpStatus.ACCEPTED);
    }

    @PutMapping("/update/{scheduleId}")
    public ResponseEntity<TrainScheduleDto> update(@PathVariable Long scheduleId, @RequestBody TrainScheduleDto trainScheduleDto){
        return new ResponseEntity<>(trainScheduleService.update(scheduleId,trainScheduleDto),HttpStatus.ACCEPTED);
    }

}

