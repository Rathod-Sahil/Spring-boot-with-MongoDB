package com.first.exception;

public class AdminDeletionException extends RuntimeException {

    public AdminDeletionException() {
        super("Admin can't be deleted");
    }

    public AdminDeletionException(String message) {
        super(message);
    }
}
