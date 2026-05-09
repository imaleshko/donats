package com.donats.backend.update.dto;

import com.donats.backend.update.FundraiserUpdateEntity;

import java.time.LocalDateTime;

public record FundraiserUpdateView(
        Long id,
        String title,
        String message,
        LocalDateTime createdAt
) {
    public static FundraiserUpdateView from(FundraiserUpdateEntity entity) {
        return new FundraiserUpdateView(
                entity.getId(),
                entity.getTitle(),
                entity.getMessage(),
                entity.getCreatedAt()
        );
    }
}
