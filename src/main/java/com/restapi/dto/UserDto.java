package com.restapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;

    @NotBlank(message = "Name required")
    private String name;
    private String email;
    private String phone;

    @Size(max = 10,min = 5, message = "password length must be between 5 and 10")
    private String password;
    private LocalDateTime localDateTime = LocalDateTime.now();

//    private UserRole role;
    private List<RoleDto> roles=new ArrayList<>();


}
