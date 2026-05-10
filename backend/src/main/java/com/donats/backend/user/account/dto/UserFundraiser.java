package com.donats.backend.user.account.dto;

import com.donats.backend.donation.DonationStatus;
import com.donats.backend.fundraiser.FundraiserEntity;
import com.donats.backend.fundraiser.FundraiserStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UserFundraiser(
        Long id,
        String title,
        String slug,
        String username,
        LocalDateTime startedAt,
        FundraiserStatus status,
        BigDecimal balance,
        Long totalDonationsCount
) {
    public static UserFundraiser from(FundraiserEntity entity) {
        long totalDonations = entity.getDonations().stream()
                .filter(donation -> donation.getStatus() == DonationStatus.SUCCESS)
                .count();

        return new UserFundraiser(
                entity.getId(),
                entity.getTitle(),
                entity.getSlug(),
                entity.getUser().getUsername(),
                entity.getStartedAt(),
                entity.getStatus(),
                entity.getBalance(),
                totalDonations
        );

    }
}
