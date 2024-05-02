package com.first.exception;

public class SamePasswordException extends RuntimeException {
    public SamePasswordException() {
        super("New password is already used by user once before");
    }
    public SamePasswordException(String message) {
        super(message);
    }
}
