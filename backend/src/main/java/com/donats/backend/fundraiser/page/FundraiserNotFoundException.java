package com.donats.backend.fundraiser.page;

public class FundraiserNotFoundException extends RuntimeException {
    public FundraiserNotFoundException(String message) {
        super(message);
    }
}
