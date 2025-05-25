package com.example.schedule.service;

import com.example.schedule.dto.request.ScheduleRequestDto;
import com.example.schedule.dto.response.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;
import com.example.schedule.exception.PasswordMismatchException;
import com.example.schedule.exception.ScheduleNotFoundException;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequestDto) {
        User user = userRepository.findUserByEmail(scheduleRequestDto.getEmail());

        Schedule schedule = new Schedule(
                scheduleRequestDto.getPost(),
                scheduleRequestDto.getPassword(),
                user
        );

        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(String update_time, String name, Long id, Integer page, Integer page_size) {
        return scheduleRepository.findAllSchedules(update_time, name, id, page, page_size);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) throws ScheduleNotFoundException {
        Schedule schedule = scheduleRepository.findScheduleById(id);
        return new ScheduleResponseDto(schedule);
    }

    @Override
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) throws PasswordMismatchException, ScheduleNotFoundException {
        Schedule schedule = scheduleRepository.findScheduleById(id);
        if (!schedule.getPassword().equals(scheduleRequestDto.getPassword())) {
            throw new PasswordMismatchException();
        }

        schedule.update(scheduleRequestDto.getPost());
        Schedule updateSchedule = scheduleRepository.updateSchedule(schedule);

        return new ScheduleResponseDto(updateSchedule);
    }

    @Override
    @Transactional
    public void deleteScheduleById(Long id, String password) throws PasswordMismatchException, ScheduleNotFoundException {
        Schedule schedule = scheduleRepository.findScheduleById(id);
        if (!schedule.getPassword().equals(password)) {
            throw new PasswordMismatchException();
        }
        scheduleRepository.deleteScheduleById(id);
    }
}
