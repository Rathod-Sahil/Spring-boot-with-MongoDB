package com.first.exception;

public class AdminCanNotBeBlocked extends RuntimeException {

    public AdminCanNotBeBlocked() {
        super("Admin can't be blocked");
    }

    public AdminCanNotBeBlocked(String message) {
        super(message);
    }
}
