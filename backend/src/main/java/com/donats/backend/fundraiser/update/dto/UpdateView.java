package com.donats.backend.fundraiser.update.dto;

import com.donats.backend.fundraiser.update.UpdateEntity;

import java.time.LocalDateTime;

public record UpdateView(
        Long id,
        String title,
        String message,
        LocalDateTime createdAt
) {
    public static UpdateView from(UpdateEntity entity) {
        return new UpdateView(
                entity.getId(),
                entity.getTitle(),
                entity.getMessage(),
                entity.getCreatedAt()
        );
    }
}
