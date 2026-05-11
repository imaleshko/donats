package com.donats.backend.exceptions;

public class DonationCloseException extends RuntimeException {
    public DonationCloseException(String message) {
        super(message);
    }
}
