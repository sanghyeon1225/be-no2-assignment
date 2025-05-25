package com.example.schedule.service;

import com.example.schedule.dto.request.UserRequestDto;
import com.example.schedule.dto.response.ScheduleResponseDto;
import com.example.schedule.dto.response.UserResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;
import com.example.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        if (userRepository.existByEmail(userRequestDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        User user = new User(userRequestDto.getName(), userRequestDto.getEmail());
        return userRepository.saveUser(user);
    }

    @Override
    @Transactional
    public UserResponseDto findUserById(Long id) {
        User user = userRepository.findUserById(id);
        return new UserResponseDto(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreate_time(),
                user.getUpdate_time());
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {

        User user = userRepository.findUserById(id);

        user.update(userRequestDto.getName());
        User updateUser = userRepository.updateUser(id, user);

        return new UserResponseDto(updateUser);
    }

    public void deleteUserById(Long id) {
        User user = userRepository.findUserById(id);
        userRepository.deleteUserById(id);
    }


}
