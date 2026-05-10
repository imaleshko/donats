package com.donats.backend.fundraiser.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record CreateFundraiserRequest(
        @NotBlank(message = "Назва не може бути порожньою")
        String title,

        @NotBlank(message = "Slug обов'язковий")
        @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug може містити тільки малі латинські літери, цифри та дефіс")
        String slug,

        @NotBlank(message = "Опис обов'язковий")
        String description,

        @Positive(message = "Ціль має бути більшою за нуль")
        BigDecimal goal,

        List<String> imageUrls
) {
}
