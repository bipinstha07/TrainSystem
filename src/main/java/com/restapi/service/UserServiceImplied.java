package com.restapi.service;

import com.restapi.dto.PageResponse;
import com.restapi.dto.UserDto;
import com.restapi.entity.Role;
import com.restapi.entity.User;
import com.restapi.exceptionhandler.ResourceNotFoundException;
import com.restapi.repository.RoleRepo;
import com.restapi.repository.UserRepo;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Getter
@Setter
@AllArgsConstructor

public class UserServiceImplied implements UserService{

    private UserRepo userRepo;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    private RoleRepo roleRepo;

    @Override
    public UserDto save(UserDto userDto) {
        User user = modelMapper.map(userDto,User.class);
        Role role = roleRepo.findByName("ROLE_ADMIN").orElseThrow(()-> new ResourceNotFoundException("No Type of role Found"));
        user.getRoles().add(role);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setLocalDateTime(LocalDateTime.now());
        User userEntity = userRepo.save(user);
        return modelMapper.map(userEntity,UserDto.class);
    }



    @Override
    public PageResponse<UserDto> getAll(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.trim().equals("asc")? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        Pageable pages = PageRequest.of(page,size,sort);

        Page<User> userEntity = userRepo.findAll(pages);
        Page<UserDto> userDto = userEntity.map(user -> modelMapper.map(user,UserDto.class));
        return  PageResponse.fromPage(userDto);

    }

    @Override
    public void deleteAll(){
        userRepo.deleteAll();
    }

    @Override
    public void delete(Long userId) {
     User user =  userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("No user Id"));
     userRepo.delete(user);
    }

    @Override
    public UserDto getUser(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("No user Id"));
         return modelMapper.map(user,UserDto.class);
    }

    @Override
    public UserDto update(Long userId, UserDto userDto) {
       User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("No user Id"));
       user.setName(userDto.getName());
       user.setEmail(userDto.getEmail());
       user.setPassword(userDto.getPassword());
       user.setPhone(userDto.getPhone());


       User saveduser = userRepo.save(user);

        return modelMapper.map(saveduser,UserDto.class);
    }
}
