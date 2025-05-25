package com.example.schedule.service;


import com.example.schedule.dto.request.ScheduleRequestDto;
import com.example.schedule.dto.request.UserRequestDto;
import com.example.schedule.dto.response.ScheduleResponseDto;
import com.example.schedule.dto.response.UserResponseDto;
import com.example.schedule.entity.User;

import java.util.List;


public interface UserService {
    UserResponseDto saveUser(UserRequestDto userRequestDto);
    // UserResponseDto findUserByEmail(String email);
    UserResponseDto findUserById(Long id);
    UserResponseDto updateUser(Long id, UserRequestDto userRequestDto);
    void deleteUserById(Long id);

}
