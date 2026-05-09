package com.donats.backend.fundraising.page.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record FundraisingResponseDto(
        Long id,
        String title,
        String slug,
        String description,
        BigDecimal balance,
        BigDecimal goal,
        LocalDate endDate,
        List<String> imageUrls,
        String authorUsername,
        String authorAvatarUrl,
        String status,
        LocalDateTime startedAt,
        LocalDateTime endedAt
) {
}
