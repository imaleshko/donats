package com.donats.backend.user;

public record User(Long id, String username, String email, String avatarUrl) {
    public static User from(UserEntity entity) {
        return new User(entity.getId(), entity.getUsername(), entity.getEmail(), entity.getAvatarUrl());
    }
}
