package com.restapi.controller.admin;

import com.restapi.aspectOriented.CustomAopAnnotation;
import com.restapi.dto.PageResponse;
import com.restapi.dto.TrainDto;
import com.restapi.service.TrainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/train")
@AllArgsConstructor
public class AdminTrain {
    private TrainService trainService;


    @Operation(
            summary = "Create Train",
            description = "This Api create Train"
    )
    @PostMapping("/create")
    public ResponseEntity<TrainDto> add(@RequestBody TrainDto trainDto){
        System.out.println(trainDto.getName());
        System.out.println(trainDto.getSourceStation());
        return new ResponseEntity<>(trainService.add(trainDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Train",
            description = "This Api fetch all the train "
    )

//    Custom AOP annotation
//    @CustomAopAnnotation
    @GetMapping("/getAll")
    public ResponseEntity<PageResponse<TrainDto>> getAll(
            @RequestParam (value = "page" , defaultValue = "0") int page,
            @RequestParam (value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir
    ){
        return new ResponseEntity<>(trainService.getAll(page,size,sortBy,sortDir),HttpStatus.OK);

    }

    @Operation(
            summary = "Get Train by ID",
            description = "This api get the train by train Id"
    )
    @ApiResponses( value={
                    @ApiResponse(responseCode = "200",description = "Success"),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error")
            }
    )
    @GetMapping("/{trainId}")
    public ResponseEntity<TrainDto> getOne(
        @Parameter(description = "Id of the train to fetch train") @PathVariable Long trainId
    ){
        return new ResponseEntity<>(trainService.getOne(trainId),HttpStatus.OK);
    }


    @Operation(
            summary = "Delete Train",
            description = "This api delete the train by train ID"
    )
    @ApiResponse(responseCode = "200",description = "Success")
    @DeleteMapping("/{trainId}")
    public ResponseEntity<String> delete(
        @Parameter(description = "Id of the train to delete the train")    @PathVariable Long trainId
    ){
        trainService.delete(trainId);
        return new ResponseEntity<>("Deletion Success",HttpStatus.ACCEPTED);
    }

    @Operation(
            summary = "Update Train",
            description = "This api Update the train by train Id"
    )
    @PutMapping("/{trainId}")
    public ResponseEntity<TrainDto> update(
            @PathVariable Long trainId,
            @RequestBody TrainDto trainDto
    ){
        return new ResponseEntity<>(trainService.update(trainId,trainDto),HttpStatus.ACCEPTED);
    }

}
