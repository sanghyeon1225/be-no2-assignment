package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class User {

    @Setter
    private Long id;
    private String name;
    private String email;
    @Setter
    private LocalDateTime create_time;
    @Setter
    private LocalDateTime update_time;

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

//    public User(Long id, String name, String email, LocalDateTime create_time, LocalDateTime update_time) {
//        this.id = id;
//        this.name = name;
//        this.email = email;
//        this.create_time = create_time;
//        this.update_time = update_time;
//    }

    public void update(String name) {
        this.name = name;
    }
}
