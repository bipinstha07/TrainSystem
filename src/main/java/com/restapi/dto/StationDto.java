package com.restapi.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class StationDto {

    private long id;

    @NotEmpty(message = "Can't be empty")
    @Min(value = 100, message = "Should be more than 100")
    @Max(value = 1000, message = "Should be less than 1000")
    private String code;
    private String name;
    private String city;
}
