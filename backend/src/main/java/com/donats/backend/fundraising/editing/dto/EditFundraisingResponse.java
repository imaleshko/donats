package com.donats.backend.fundraising.editing.dto;

import java.math.BigDecimal;
import java.util.List;

public record EditFundraisingResponse(
        Long id,
        String title,
        String slug,
        String description,
        BigDecimal goal,
        List<String> existingImagesUrls
) {
}
