package com.donats.backend.fundraiser.edit.dto;

import com.donats.backend.fundraiser.FundraiserEntity;
import com.donats.backend.fundraiser.tag.TagEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record EditFundraiserResponse(
        Long id,
        String title,
        String slug,
        String description,
        BigDecimal goal,
        List<String> existingImagesUrls,
        Set<String> tags

) {
    public static EditFundraiserResponse from(FundraiserEntity entity) {
        return new EditFundraiserResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getSlug(),
                entity.getDescription(),
                entity.getGoal(),
                entity.getImageUrls(),
                entity.getTags().stream().map(TagEntity::getName).collect(Collectors.toSet())
        );
    }
}
