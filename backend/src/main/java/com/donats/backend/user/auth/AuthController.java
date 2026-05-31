package com.donats.backend.user.auth;

import com.donats.backend.user.auth.dto.AuthResponse;
import com.donats.backend.user.auth.dto.LoginRequest;
import com.donats.backend.user.auth.dto.RegisterRequest;
import com.donats.backend.user.auth.dto.Tokens;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final long cookieMaxAge;

    public AuthController(AuthService authService, @Value("${jwt.refreshExpiration}") long refreshExpiration) {
        this.authService = authService;
        this.cookieMaxAge = refreshExpiration / 1000;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        Tokens tokens = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, createCookie(tokens.refreshToken(), cookieMaxAge).toString())
                .body(new AuthResponse(tokens.accessToken()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        Tokens tokens = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, createCookie(tokens.refreshToken(), cookieMaxAge).toString())
                .body(new AuthResponse(tokens.accessToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@CookieValue(name = "refreshToken") String refreshToken) {
        Tokens tokens = authService.refreshToken(refreshToken);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, createCookie(tokens.refreshToken(), cookieMaxAge).toString())
                .body(new AuthResponse(tokens.accessToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
        if (refreshToken != null) {
            authService.logout(refreshToken);
        }

        ResponseCookie cookie = createCookie("", 0L);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    private ResponseCookie createCookie(String token, long maxAge) {
        return ResponseCookie.from("refreshToken", token)
                .httpOnly(true)
                .path("/api/auth")
                .maxAge(maxAge)
                .build();
    }
}
