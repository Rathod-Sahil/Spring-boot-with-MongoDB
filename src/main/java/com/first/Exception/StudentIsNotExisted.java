package com.first.Exception;

public class StudentIsNotExisted extends RuntimeException {
    public StudentIsNotExisted(){
        super("Student with given id is not existed in database");
    }

    public StudentIsNotExisted(String message){
        super(message);
    }
}
