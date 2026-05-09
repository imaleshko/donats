package com.donats.backend.fundraiser.creating.exception;

public class SlugAlreadyInUseException extends RuntimeException {
    public SlugAlreadyInUseException(String message) {
        super(message);
    }
}
