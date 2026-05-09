package com.donats.backend.update.dto;

import com.donats.backend.update.FundraisingUpdateEntity;

import java.time.LocalDateTime;

public record FundraisingUpdateView(
        Long id,
        String title,
        String message,
        LocalDateTime createdAt
) {
    public static FundraisingUpdateView from(FundraisingUpdateEntity entity) {
        return new FundraisingUpdateView(
                entity.getId(),
                entity.getTitle(),
                entity.getMessage(),
                entity.getCreatedAt()
        );
    }
}
