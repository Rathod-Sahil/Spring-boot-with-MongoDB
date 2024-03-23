package com.first.Exception;

public class StudentIsAlreadyExisted extends RuntimeException {

    public StudentIsAlreadyExisted(){
        super("Student with given email id is already existed in database");
    }

    public StudentIsAlreadyExisted(String message){
        super(message);
    }
}
