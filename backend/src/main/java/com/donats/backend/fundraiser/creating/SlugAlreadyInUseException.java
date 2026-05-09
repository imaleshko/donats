package com.donats.backend.fundraiser.creating;

public class SlugAlreadyInUseException extends RuntimeException {
    public SlugAlreadyInUseException(String message) {
        super(message);
    }
}
