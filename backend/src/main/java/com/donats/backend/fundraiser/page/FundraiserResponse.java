package com.donats.backend.fundraiser.page;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record FundraiserResponse(
        Long id,
        String title,
        String slug,
        String description,
        BigDecimal balance,
        BigDecimal goal,
        List<String> imageUrls,
        String authorUsername,
        String authorAvatarUrl,
        String status,
        LocalDateTime startedAt,
        LocalDateTime endedAt
) {
}
