package com.nelumbo.user.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(RuntimeException e) {
        super(e);
    }
}