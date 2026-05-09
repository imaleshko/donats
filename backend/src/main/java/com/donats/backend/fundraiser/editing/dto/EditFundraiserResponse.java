package com.donats.backend.fundraiser.editing.dto;

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
}
