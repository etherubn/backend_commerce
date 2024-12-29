package com.catdog.comerce.controller;

import com.catdog.comerce.dto.GenericResponse;
import com.catdog.comerce.dto.request.UserDto;
import com.catdog.comerce.dto.response.ResponseUserDto;
import com.catdog.comerce.service.IUserService;
import com.catdog.comerce.utils.MapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final MapperUtil mapperUtil;
    private final IUserService userService;


    @GetMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GenericResponse<ResponseUserDto>> findAllUsers(){
        return new ResponseEntity<>(new GenericResponse<>(200,"success", userService.findAllUsers()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GenericResponse<ResponseUserDto>> findUser(@PathVariable Long id){
        ResponseUserDto responseUserDto = mapperUtil.map(userService.getById(id), ResponseUserDto.class);

        return new ResponseEntity<>(new GenericResponse<>(200,"success", Arrays.asList(responseUserDto)), HttpStatus.OK);
    }


    @PreAuthorize("permitAll()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/{id}")
    public ResponseEntity<GenericResponse<ResponseUserDto>> updateUserInfomation(@RequestBody @Valid UserDto userDto,@PathVariable Long id){
        return new ResponseEntity<>(new GenericResponse<>(200,"success",Arrays.asList(userService.updateInformation(userDto,id))),HttpStatus.OK);
    }







}
