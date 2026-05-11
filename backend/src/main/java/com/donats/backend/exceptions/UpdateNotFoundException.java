package com.donats.backend.exceptions;

public class UpdateNotFoundException extends RuntimeException {
    public UpdateNotFoundException(String message) {
        super(message);
    }
}
