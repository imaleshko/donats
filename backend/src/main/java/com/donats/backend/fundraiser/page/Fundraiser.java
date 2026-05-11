package com.donats.backend.fundraiser.page;

import com.donats.backend.fundraiser.FundraiserEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record Fundraiser(
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
        LocalDateTime closedAt
) {
    public static Fundraiser from(FundraiserEntity entity) {
        return new Fundraiser(
                entity.getId(),
                entity.getTitle(),
                entity.getSlug(),
                entity.getDescription(),
                entity.getBalance(),
                entity.getGoal(),
                entity.getImageUrls(),
                entity.getUser().getUsername(),
                entity.getUser().getAvatarUrl(),
                entity.getStatus().name(),
                entity.getStartedAt(),
                entity.getClosedAt()
        );
    }
}
