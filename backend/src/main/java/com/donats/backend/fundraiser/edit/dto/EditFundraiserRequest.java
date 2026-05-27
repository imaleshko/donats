package com.donats.backend.fundraiser.edit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public record EditFundraiserRequest(
        @NotBlank(message = "Назва обов'язкова")
        String title,

        @NotBlank(message = "Slug обов'язковий")
        @Pattern(regexp = "^[a-z0-9-]+$", message = "Тільки малі латинські літери, цифри та дефіс")
        String slug,

        @NotBlank(message = "Опис обов'язковий")
        String description,

        BigDecimal goal,

        @NotEmpty(message = "Додайте хоча б одне зображення")
        List<String> imageUrls,

        @NotEmpty(message = "Додайте тег")
        @Size(max = 5, message = "Максимум 5 тегів")
        Set<@NotBlank(message = "Тег не може бути порожнім")
        @Length(min = 2, max = 25, message = "Тег має містити від 2 до 25 символів")
                String> tags
) {
}
