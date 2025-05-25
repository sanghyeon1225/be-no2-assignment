package com.example.schedule.dto.response;

import com.example.schedule.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {
    private final Long id;
    private final String name;
    private final String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDateTime create_time;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDateTime update_time;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.create_time = user.getCreate_time();
        this.update_time = user.getUpdate_time();
    }

    public UserResponseDto(Long id, String name, String email, LocalDateTime create_time, LocalDateTime update_time) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.create_time = create_time;
        this.update_time = update_time;
    }


}
