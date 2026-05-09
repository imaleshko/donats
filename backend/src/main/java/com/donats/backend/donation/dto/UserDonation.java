package com.donats.backend.donation.dto;

import com.donats.backend.donation.DonationEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UserDonation(
        Long id,
        String name,
        BigDecimal amount,
        LocalDateTime createdAt,
        String message,
        String fundraiserTitle,
        String fundraiserSlug,
        String authorUsername
) {
    public static UserDonation from(DonationEntity entity) {
        return new UserDonation(
                entity.getId(),
                entity.getName(),
                entity.getAmount(),
                entity.getCreatedAt(),
                entity.getMessage(),
                entity.getFundraiser().getTitle(),
                entity.getFundraiser().getSlug(),
                entity.getFundraiser().getUser().getUsername()
        );
    }
}
