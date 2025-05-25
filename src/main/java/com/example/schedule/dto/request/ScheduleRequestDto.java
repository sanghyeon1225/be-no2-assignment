package com.example.schedule.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    @NotBlank
    @Size(max = 200)
    private String post;
    @NotBlank
    private String password;
    @NotBlank
    private String email;

}
