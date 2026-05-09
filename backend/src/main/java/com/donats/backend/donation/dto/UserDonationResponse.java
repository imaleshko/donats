package com.donats.backend.donation.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UserDonationResponse(
        Long id,
        String name,
        BigDecimal amount,
        LocalDateTime createdAt,
        String message,
        String fundraiserTitle,
        String fundraiserSlug,
        String authorUsername
) {
}
