package com.first.exception;

public class StudentIsNotExisted extends RuntimeException {
    public StudentIsNotExisted(){
        super("Student is not found");
    }

    public StudentIsNotExisted(String message){
        super(message);
    }
}
