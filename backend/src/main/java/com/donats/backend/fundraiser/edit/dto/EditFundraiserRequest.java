package com.donats.backend.fundraiser.edit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.util.List;

public record EditFundraiserRequest(
        @NotBlank(message = "Назва обов'язкова")
        String title,

        @NotBlank(message = "Slug обов'язковий")
        @Pattern(regexp = "^[a-z0-9-]+$", message = "Тільки малі літери, цифри та дефіс")
        String slug,

        @NotBlank(message = "Опис обов'язковий")
        String description,

        BigDecimal goal,

        List<String> imageUrls
) {
}
