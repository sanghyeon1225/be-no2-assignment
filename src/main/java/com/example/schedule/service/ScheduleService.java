package com.example.schedule.service;

import com.example.schedule.dto.request.ScheduleRequestDto;
import com.example.schedule.dto.response.ScheduleResponseDto;
import com.example.schedule.exception.PasswordMismatchException;
import com.example.schedule.exception.ScheduleNotFoundException;

import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequestDto);
    List<ScheduleResponseDto> findAllSchedules(String update_time, String name, Long id, Integer page, Integer page_size);
    ScheduleResponseDto findScheduleById(Long id) throws ScheduleNotFoundException;
    ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) throws PasswordMismatchException, ScheduleNotFoundException;
    void deleteScheduleById(Long id, String password) throws PasswordMismatchException, ScheduleNotFoundException;
}
