package com.restapi.controller.admin;

import com.restapi.dto.TrainSeatDto;
import com.restapi.service.TrainSeatService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/train-seats")
@AllArgsConstructor
public class AdminTrainSeat {
    private TrainSeatService trainSeatService;

    @PostMapping
    public ResponseEntity<TrainSeatDto> add(@RequestBody TrainSeatDto trainSeatDto){
        return new ResponseEntity<>(trainSeatService.add(trainSeatDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TrainSeatDto>> getAll(){
        return new ResponseEntity<>(trainSeatService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/getBySchedule/{trainScheduleId}")
    public ResponseEntity<List<TrainSeatDto>> getAllByTrainSchedule(@PathVariable Long trainScheduleId){
        return new ResponseEntity<>(trainSeatService.getByTrainScheduleId(trainScheduleId),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        trainSeatService.delete(id);
        return new ResponseEntity<>("Train Seat Deletion Success",HttpStatus.ACCEPTED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TrainSeatDto> update(@PathVariable  Long id, @RequestBody TrainSeatDto trainSeatDto){
        return new ResponseEntity<>(trainSeatService.update(id,trainSeatDto),HttpStatus.ACCEPTED);

    }


}
