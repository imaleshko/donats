package com.donats.backend.account.dto;

import com.donats.backend.fundraiser.FundraiserStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UsersFundraiserResponse(
        Long id,
        String title,
        String slug,
        String username,
        LocalDateTime startedAt,
        FundraiserStatus status,
        BigDecimal balance,
        Long totalDonationsCount
) {
}
