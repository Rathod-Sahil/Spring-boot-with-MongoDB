package com.first.exception;

public class StudentNotExistedException extends RuntimeException {
    public StudentNotExistedException(){
        super("Student is not found");
    }

    public StudentNotExistedException(String message){
        super(message);
    }
}
