package com.donats.backend.user.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ChangeUsernameRequest(
        @NotBlank(message = "Нікнейм не може бути порожнім")
        @Size(min = 3, max = 20, message = "Нікнейм повинен містити від 3 до 20 символів")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Нікнейм може містити лише латинські літери, цифри та нижнє підкреслення")
        String username) {
}
