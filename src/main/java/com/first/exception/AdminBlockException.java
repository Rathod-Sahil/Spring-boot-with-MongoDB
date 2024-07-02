package com.first.exception;

public class AdminBlockException extends RuntimeException {

    public AdminBlockException() {
        super("Admin can't be blocked");
    }

    public AdminBlockException(String message) {
        super(message);
    }
}
