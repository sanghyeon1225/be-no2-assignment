package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {
    @Setter
    private Long id;
    private String post;
    private String password;

    // 필수 기능
    // private String name;

    // 도전 기능
    private User user;

    @Setter
    private LocalDateTime create_time;
    @Setter
    private LocalDateTime update_time;

    // 필수 기능
//    public Schedule(String post, String password, String name) {
//        this.post = post;
//        this.password = password;
//        this.name = name;
//    }
//
//    public void update(String post, String name) {
//        this.post = post;
//        this.name = name;
//    }

    // 도전 기능
    public Schedule(String post, String password, User user) {
        this.post = post;
        this.password = password;
        this.user = user;
    }

    public void update(String post) {
        this.post = post;
    }
}
