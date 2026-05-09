package com.donats.backend.fundraiser.update.dto;

import com.donats.backend.fundraiser.update.UpdateEntity;

import java.time.LocalDateTime;

public record Update(
        Long id,
        String title,
        String message,
        LocalDateTime createdAt
) {
    public static Update from(UpdateEntity entity) {
        return new Update(
                entity.getId(),
                entity.getTitle(),
                entity.getMessage(),
                entity.getCreatedAt()
        );
    }
}
