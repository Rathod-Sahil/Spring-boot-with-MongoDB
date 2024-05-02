package com.first.exception;

public class AdminCanNotBeDeleted extends RuntimeException {

    public AdminCanNotBeDeleted() {
        super("Admin can't be deleted");
    }

    public AdminCanNotBeDeleted(String message) {
        super(message);
    }
}
