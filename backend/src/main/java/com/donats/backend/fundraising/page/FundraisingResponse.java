package com.donats.backend.fundraising.page;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record FundraisingResponse(
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
