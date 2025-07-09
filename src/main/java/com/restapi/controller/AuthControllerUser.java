package com.restapi.controller;

import com.restapi.config.security.JwtHelper;
import com.restapi.dto.ErrorResponse;
import com.restapi.dto.JwtResponse;
import com.restapi.dto.LoginRequest;
import com.restapi.dto.UserDto;
import com.restapi.service.UserServiceImplied;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
            String token = this.jwtHelper.generateToken(userDetails);
            JwtResponse jwtResponse = new JwtResponse(token,userDetails.getUsername());

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



}
