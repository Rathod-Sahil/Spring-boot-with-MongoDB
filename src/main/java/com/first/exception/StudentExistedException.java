package com.first.exception;

public class StudentExistedException extends RuntimeException {

    public StudentExistedException(String message){
        super(message);
    }
}
