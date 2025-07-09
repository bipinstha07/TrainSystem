package com.restapi.service;

import com.restapi.dto.PageResponse;
import com.restapi.dto.UserDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public interface UserService {
    UserDto save(UserDto userDto);

    PageResponse<UserDto> getAll(int page, int size, String sortBy, String sortDir);

    void delete(Long userId);

    UserDto getUser(Long userId);

    UserDto update(Long userId, UserDto userDto);
}
