package com.example.schedule.dto.response;

import com.example.schedule.entity.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {
    private final Long id;
    private final String post;
    private final String name;
    private final String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDateTime create_time;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDateTime update_time;


    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.post = schedule.getPost();
        this.name = schedule.getUser().getName();
        this.email = schedule.getUser().getEmail();
        this.create_time = schedule.getCreate_time();
        this.update_time = schedule.getUpdate_time();
    }

    public ScheduleResponseDto(Long id, String post, String name, String email, LocalDateTime create_time, LocalDateTime update_time) {
        this.id = id;
        this.post = post;
        this.name = name;
        this.email = email;
        this.create_time = create_time;
        this.update_time = update_time;
    }

}
