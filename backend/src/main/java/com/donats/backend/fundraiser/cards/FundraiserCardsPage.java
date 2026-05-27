package com.donats.backend.fundraiser.cards;

import java.util.List;

public record FundraiserCardsPage(
        List<FundraiserCard> fundraisers,
        boolean hasMore
) {
}
