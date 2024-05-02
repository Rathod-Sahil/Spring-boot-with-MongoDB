package com.first.exception;

public class StudentIsNotBlocked extends RuntimeException {

    public StudentIsNotBlocked() {
        super("Student is not blocked");
    }

    public StudentIsNotBlocked(String message) {
        super(message);
    }
}
