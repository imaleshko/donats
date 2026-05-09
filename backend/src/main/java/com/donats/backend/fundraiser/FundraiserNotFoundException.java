package com.donats.backend.fundraiser;

public class FundraiserNotFoundException extends RuntimeException {
    public FundraiserNotFoundException(String message) {
        super(message);
    }
}
