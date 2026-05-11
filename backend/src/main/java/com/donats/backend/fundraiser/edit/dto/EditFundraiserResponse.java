package com.donats.backend.fundraiser.edit.dto;

import com.donats.backend.fundraiser.FundraiserEntity;

import java.math.BigDecimal;
import java.util.List;

public record EditFundraiserResponse(
        Long id,
        String title,
        String slug,
        String description,
        BigDecimal goal,
        List<String> existingImagesUrls
) {
    public static EditFundraiserResponse from(FundraiserEntity entity) {
        return new EditFundraiserResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getSlug(),
                entity.getDescription(),
                entity.getGoal(),
                entity.getImageUrls()
        );
    }
}
