package com.donats.backend.fundraising.page;

public class FundraisingNotFoundException extends RuntimeException {
    public FundraisingNotFoundException(String message) {
        super(message);
    }
}
