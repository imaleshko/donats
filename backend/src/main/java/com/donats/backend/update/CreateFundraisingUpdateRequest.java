package com.donats.backend.update;

import jakarta.validation.constraints.NotBlank;

public record CreateFundraisingUpdateRequest(
        @NotBlank(message = "Назва обов'язкова")
        String title,

        @NotBlank(message = "Текст апдейту обов'язковий")
        String message
) {
}
