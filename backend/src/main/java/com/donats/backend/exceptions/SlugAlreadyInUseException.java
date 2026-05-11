package com.donats.backend.exceptions;

public class SlugAlreadyInUseException extends RuntimeException {
    public SlugAlreadyInUseException(String message) {
        super(message);
    }
}
