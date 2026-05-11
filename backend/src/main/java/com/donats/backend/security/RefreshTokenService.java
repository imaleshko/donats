package com.donats.backend.security;

import com.donats.backend.user.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final long refreshTokenExpiration;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository,
                               @Value("${jwt.refreshExpiration}") long refreshTokenExpiration) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    @Transactional
    public RefreshTokenEntity createRefreshToken(UserEntity user) {
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpiration));

        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional(readOnly = true)
    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public boolean isTokenExpired(RefreshTokenEntity refreshToken) {
        return refreshToken.getExpiryDate().isBefore(Instant.now());
    }

    @Transactional
    public void deleteByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
