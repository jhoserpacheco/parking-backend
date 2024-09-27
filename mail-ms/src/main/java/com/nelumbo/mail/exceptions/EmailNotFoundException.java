package com.nelumbo.mail.exceptions;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String message) {
        super(message);
    }

    public EmailNotFoundException(RuntimeException e) {
        super(e);
    }
}