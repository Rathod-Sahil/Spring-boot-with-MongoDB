package com.first.exception;


public class StudentValidationException extends RuntimeException {

    public StudentValidationException(String message) {
        super(message);
    }

}
