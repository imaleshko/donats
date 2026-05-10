package com.donats.backend.user.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        @NotBlank(message = "Поточний пароль обов'язковий")
        String oldPassword,

        @NotBlank(message = "Новий пароль обов'язковий")
        @Size(min = 8, message = "Новий пароль повинен містити щонайменше 8 символів")
        String newPassword) {
}
