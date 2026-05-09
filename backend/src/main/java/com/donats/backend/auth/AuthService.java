package com.donats.backend.auth;

import com.donats.backend.auth.dto.LoginRequest;
import com.donats.backend.auth.dto.RegisterRequest;
import com.donats.backend.auth.dto.Tokens;
import com.donats.backend.exceptions.InvalidTokenException;
import com.donats.backend.exceptions.TokenExpiredException;
import com.donats.backend.exceptions.UserAlreadyExistsException;
import com.donats.backend.exceptions.UserNotFoundException;
import com.donats.backend.security.AccessTokenService;
import com.donats.backend.security.RefreshTokenEntity;
import com.donats.backend.security.RefreshTokenService;
import com.donats.backend.user.UserEntity;
import com.donats.backend.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AccessTokenService accessTokenService, RefreshTokenService refreshTokenService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.accessTokenService = accessTokenService;
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
    }

    public Tokens register(RegisterRequest request) {
        if (userRepository.existsByEmailOrUsername(request.email(), request.username())
        ) {
            throw new UserAlreadyExistsException("Користувач з таким email або ім'ям вже існує");
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        UserEntity savedUser = userRepository.save(user);

        return generateTokensForUser(savedUser);
    }

    public Tokens login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        UserEntity user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException("Користувача з таким email не знайдено"));

        return generateTokensForUser(user);
    }

    public Tokens refreshToken(String refreshToken) {
        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenEntity -> {
                    if (refreshTokenService.isTokenExpired(refreshTokenEntity)) {
                        refreshTokenService.deleteByToken(refreshToken);
                        throw new TokenExpiredException("Час дії Refresh токена минув");
                    }
                    return refreshTokenEntity.getUser();
                })
                .map(user -> {
                    refreshTokenService.deleteByToken(refreshToken);
                    return generateTokensForUser(user);
                })
                .orElseThrow(() -> new InvalidTokenException("Недійсний Refresh токен"));
    }

    public void logout(String refreshToken) {
        refreshTokenService.deleteByToken(refreshToken);
    }

    private Tokens generateTokensForUser(UserEntity user) {
        String accessToken = accessTokenService.generateAccessToken(user.getEmail());
        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(user);

        return new Tokens(accessToken, refreshToken.getToken());
    }
}
