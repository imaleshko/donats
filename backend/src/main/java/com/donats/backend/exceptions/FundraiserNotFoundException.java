package com.donats.backend.exceptions;

public class FundraiserNotFoundException extends RuntimeException {
    public FundraiserNotFoundException(String message) {
        super(message);
    }
}
