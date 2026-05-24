package com.donats.backend.fundraiser.create;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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

        @NotEmpty(message = "Додайте хоча б одне зображення")
        List<String> imageUrls,

        @NotEmpty(message = "Додайте тег")
        @Size(max = 5, message = "Максимум 5 тегів")
        Set<@Length(max = 25, message = "Максимум 25 символів") String> tags
) {
}
