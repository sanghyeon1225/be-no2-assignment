package com.example.schedule.repository;

import com.example.schedule.dto.response.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.exception.ScheduleNotFoundException;

import java.util.List;

public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);
    List<ScheduleResponseDto> findAllSchedules(String update_time, String name, Long id, Integer page, Integer page_size);
    Schedule findScheduleById(Long id) throws ScheduleNotFoundException;
    Schedule updateSchedule(Schedule schedule) throws ScheduleNotFoundException;
    void deleteScheduleById(Long id);
}
