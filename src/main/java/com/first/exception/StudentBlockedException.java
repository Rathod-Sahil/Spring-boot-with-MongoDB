package com.first.exception;

public class StudentBlockedException extends RuntimeException {

    public StudentBlockedException() {
        super("Student is blocked, contact admin for unblock");
    }

    public StudentBlockedException(String message) {
        super(message);
    }
}
