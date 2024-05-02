package com.first.exception;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {
        super("Enter correct old password");
    }

    public WrongPasswordException(String message) {
        super(message);
    }
}
