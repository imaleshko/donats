package com.donats.backend.user.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "Електронна пошта не може бути порожньою")
        @Email(message = "Некоректний формат електронної пошти")
        String email,

        @NotBlank(message = "Пароль обов'язковий")
        @Size(min = 8, message = "Новий пароль повинен містити щонайменше 8 символів")
        String password) {
}
