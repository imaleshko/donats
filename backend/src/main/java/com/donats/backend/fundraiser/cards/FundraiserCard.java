package com.donats.backend.fundraiser.cards;

import com.donats.backend.fundraiser.FundraiserEntity;
import com.donats.backend.fundraiser.create.tag.TagEntity;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public record FundraiserCard(
        Long id,
        String title,
        String author,
        BigDecimal balance,
        BigDecimal goal,
        String slug,
        Set<String> tags
) {
    public static FundraiserCard from(FundraiserEntity entity) {
        return new FundraiserCard(
                entity.getId(),
                entity.getTitle(),
                entity.getUser().getUsername(),
                entity.getBalance(),
                entity.getGoal(),
                entity.getSlug(),
                entity.getTags().stream().map(TagEntity::getName).collect(Collectors.toSet())
        );
    }
}
