package com.restapi.controller.user;

import com.restapi.dto.BookingDto;
import com.restapi.service.BookingServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user/booking")
@AllArgsConstructor
public class AdminBooking {
    private BookingServiceImp bookingServiceImp;


    @Operation(
            summary = "Save Booking",
            description = "This API saved Booking"
    )
    @ApiResponses( value={
            @ApiResponse(responseCode = "200",description = "Success"),
            @ApiResponse(responseCode = "500",description = "Internal Server Error")
    }
    )
    @PostMapping()
    public ResponseEntity<BookingDto> save(@RequestBody BookingDto bookingDto){
      return new ResponseEntity<>(bookingServiceImp.save(bookingDto), HttpStatus.CREATED);

    }


    @Operation(
            summary = "Delete Booking",
            description = "This API delete booking by bookingId"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "500", description = "Internal Servre Error")
            }
    )
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<String> delete(@PathVariable long  bookingId){
        bookingServiceImp.delete(bookingId);
        return new ResponseEntity<>("Deletion Success",HttpStatus.OK);
    }


}
