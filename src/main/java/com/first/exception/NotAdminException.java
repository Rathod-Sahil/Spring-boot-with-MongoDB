package com.first.exception;

public class NotAdminException extends RuntimeException {

    public NotAdminException() {
        super("Only admin can unblock account");
    }

    public NotAdminException(String message) {
        super(message);
    }
}
