package com.donats.backend.donation.dto;

import com.donats.backend.donation.DonationEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DonationView(
        Long id,
        String name,
        BigDecimal amount,
        LocalDateTime createdAt,
        String message
) {
    public static DonationView from(DonationEntity entity) {
        return new DonationView(
                entity.getId(),
                entity.getName(),
                entity.getAmount(),
                entity.getCreatedAt(),
                entity.getMessage()
        );
    }
}
