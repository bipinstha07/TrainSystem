package com.restapi.controller.admin;

import com.restapi.dto.TrainRouteDto;
import com.restapi.service.TrainRouteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/routes")
@AllArgsConstructor
public class AdminTrainRoutes {
    private TrainRouteService trainRouteService;

    @PostMapping
    public TrainRouteDto add(
            @RequestBody TrainRouteDto trainRouteDto
    ){
       return trainRouteService.addRoute(trainRouteDto);
    }

    @GetMapping("/{trainId}")
    public List<TrainRouteDto> getByTrainId(@PathVariable Long trainId){
        return trainRouteService.getRoutesByTrain(trainId);
    }

    @PutMapping("/{id}")
    public TrainRouteDto update(
            @PathVariable Long id,
            @RequestBody TrainRouteDto trainRouteDto
    ){
        return trainRouteService.update(id,trainRouteDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        trainRouteService.delete(id);
    }

}
