package com.donats.backend.user.account.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangeAvatarRequest(
        @NotBlank(message = "URL аватарки не може бути порожнім")
        String avatarUrl
) {
}
