package com.first.exception;

public class WrongOTPException extends RuntimeException {

    public WrongOTPException(String message){
        super(message);
    }
}
