package com.example.schedule.exception;

public class PasswordMismatchException extends Exception {
    public PasswordMismatchException() {
        super("게시글의 비밀번호와 일치하지 않은 비밀번호 입력");
    }
}
