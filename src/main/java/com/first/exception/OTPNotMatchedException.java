package com.first.exception;

public class OTPNotMatchedException extends RuntimeException {

    public OTPNotMatchedException(String message){
        super(message);
    }
}
