package com.first.exception;

public class StudentIsAlreadyExisted extends RuntimeException {

    public StudentIsAlreadyExisted(){
        super("Student is already existed");
    }

    public StudentIsAlreadyExisted(String message){
        super(message);
    }
}
