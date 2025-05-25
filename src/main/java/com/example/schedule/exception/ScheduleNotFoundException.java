package com.example.schedule.exception;

public class ScheduleNotFoundException extends Exception {
    public ScheduleNotFoundException() {
        super("유효하지 않은 일정에 접근");
    }
}
