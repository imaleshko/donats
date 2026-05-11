package com.donats.backend.fundraiser.cards;

import com.donats.backend.fundraiser.FundraiserEntity;

import java.math.BigDecimal;

public record FundraiserCard(
        Long id,
        String title,
        String author,
        BigDecimal balance,
        BigDecimal goal,
        String slug
) {
    public static FundraiserCard from(FundraiserEntity entity) {
        return new FundraiserCard(
                entity.getId(),
                entity.getTitle(),
                entity.getUser().getUsername(),
                entity.getBalance(),
                entity.getGoal(),
                entity.getSlug()
        );
    }
}
