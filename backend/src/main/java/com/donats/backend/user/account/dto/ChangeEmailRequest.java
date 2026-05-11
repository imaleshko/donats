package com.donats.backend.user.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ChangeEmailRequest(
        @NotBlank(message = "Електронна пошта не може бути порожньою")
        @Email(message = "Некоректний формат електронної пошти")
        String email) {
}
