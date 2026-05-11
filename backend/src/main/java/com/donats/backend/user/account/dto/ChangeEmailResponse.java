package com.donats.backend.user.account.dto;

import com.donats.backend.user.User;

public record ChangeEmailResponse(User user, String accessToken) {
}
