package com.donats.backend.user.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Нікнейм не може бути порожнім")
        @Size(min = 3, max = 20, message = "Нікнейм повинен містити від 3 до 20 символів")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Нікнейм може містити лише латинські літери, цифри та нижнє підкреслення")
        String username,

        @NotBlank(message = "Електронна пошта не може бути порожньою")
        @Email(message = "Некоректний формат електронної пошти")
        String email,

        @NotBlank(message = "Пароль обов'язковий")
        @Size(min = 8, message = "Новий пароль повинен містити щонайменше 8 символів")
        String password) {
}
