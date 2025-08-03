package com.restapi.controller.admin;

import com.restapi.config.security.JwtHelper;
import com.restapi.dto.ErrorResponse;
import com.restapi.dto.JwtResponse;
import com.restapi.dto.LoginRequest;
import com.restapi.dto.UserDto;
import com.restapi.entity.User;
import com.restapi.repository.UserRepo;
import com.restapi.service.UserServiceImplied;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthControllerUser {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private AuthenticationManager authenticationManager;
        private UserDetailsService userDetailsService;
        private JwtHelper jwtHelper;
        private UserServiceImplied userServiceImplied;



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try{
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.username(),loginRequest.password());
            this.authenticationManager.authenticate(authenticationToken);

            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.username());
            User user = userRepo.findByEmail(loginRequest.username()).orElse(null);
            String accessToken = this.jwtHelper.generateAccessToken(userDetails);
            String refreshToken = this.jwtHelper.generateRefreshToken(userDetails);
            JwtResponse jwtResponse = new JwtResponse(accessToken,refreshToken,modelMapper.map(user,UserDto.class));

        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid Credentials",401,false);
            return  new ResponseEntity<>(errorResponse ,HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("/user/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        return new ResponseEntity<>(userServiceImplied.save(userDto),HttpStatus.CREATED);
    }


    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody(required = false) String refreshToken){

        if(refreshToken == null){
            ErrorResponse er = new ErrorResponse("No Refresh Token",400,false);
            return new ResponseEntity<>(er,HttpStatus.BAD_REQUEST);
        }

        if(!jwtHelper.isRefreshToken(refreshToken)){
            ErrorResponse er = new ErrorResponse("Not a Refresh Token",500,false);
            return new ResponseEntity<>(er,HttpStatus.BAD_REQUEST);
        }

        String username = jwtHelper.getUserNameFromToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(jwtHelper.isTokenValid(refreshToken,userDetails)){
            String accessToken = jwtHelper.generateAccessToken(userDetails);
            String refreshToken1 = jwtHelper.generateRefreshToken(userDetails);
            UserDto userDto = modelMapper.map(userRepo.findByEmail(userDetails.getUsername()).orElse(null),UserDto.class);

            return new ResponseEntity<>(new JwtResponse(accessToken,refreshToken1,userDto),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new ErrorResponse("Invalid Authentication",400,false),HttpStatus.BAD_REQUEST);
        }

    }


}
