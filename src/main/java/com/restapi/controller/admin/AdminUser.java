package com.restapi.controller.admin;

import com.restapi.dto.PageResponse;
import com.restapi.dto.UserDto;
import com.restapi.service.UserServiceImplied;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
public class AdminUser {


    private UserServiceImplied userService;

    public AdminUser(UserServiceImplied userService) {
        this.userService = userService;
    }

//    @PostMapping()
//    public ResponseEntity<UserDto> user(@Valid @RequestBody UserDto userDto){
//       return  new ResponseEntity<>(userService.save(userDto), HttpStatus.OK);
//    }

    @GetMapping()
    public PageResponse<UserDto> listUser(
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="size", defaultValue = "6") int size,
            @RequestParam(value="sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value="sortDir", defaultValue = "asc") String sortDir
    ){
        return userService.getAll(page,size,sortBy,sortDir);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> delete(@PathVariable Long userId){
        userService.delete(userId);
        return new ResponseEntity<>("Deletion Success",HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAll(){;
        userService.deleteAll();
        return new ResponseEntity<>("Deletion Success",HttpStatus.ACCEPTED);
    }


    @GetMapping("/{userId}" )
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId){
       return new ResponseEntity<>(userService.getUser(userId),HttpStatus.OK);
    }

   @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateuser(
            @PathVariable Long userId,
            @RequestBody UserDto userDto
    ){
        return new ResponseEntity<>(userService.update(userId,userDto),HttpStatus.OK);
    }


}
