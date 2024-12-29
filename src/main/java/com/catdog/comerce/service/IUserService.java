package com.catdog.comerce.service;

import com.catdog.comerce.dto.request.UserDto;
import com.catdog.comerce.dto.response.ResponseUserDto;
import com.catdog.comerce.entity.User;

import java.util.List;

public interface IUserService extends ICrudService<UserDto,Long>{
    ResponseUserDto updateInformation(UserDto userDto, Long id);
    List<ResponseUserDto> findAllUsers();
}
