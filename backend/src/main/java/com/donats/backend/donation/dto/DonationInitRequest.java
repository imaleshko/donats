package com.donats.backend.donation.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DonationInitRequest(
        @NotNull Long fundraiserId,
        @NotNull @DecimalMin("1.0") BigDecimal amount,
        @NotBlank String name,
        String message
) {
}
