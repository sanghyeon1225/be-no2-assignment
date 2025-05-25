package com.example.schedule.repository;

import com.example.schedule.dto.response.UserResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;

public interface UserRepository {
    UserResponseDto saveUser(User user);
    User findUserByEmail(String email);
    User findUserById(Long id);
    User updateUser(Long id, User user);
    void deleteUserById(Long id);
    boolean existByEmail(String email);
}
