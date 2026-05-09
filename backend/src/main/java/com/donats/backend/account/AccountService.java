package com.donats.backend.account;

import com.donats.backend.account.dto.User;
import com.donats.backend.exceptions.EmailAlreadyInUseException;
import com.donats.backend.exceptions.InvalidPasswordException;
import com.donats.backend.exceptions.UserNotFoundException;
import com.donats.backend.exceptions.UsernameAlreadyInUseException;
import com.donats.backend.image.ImageService;
import com.donats.backend.user.UserEntity;
import com.donats.backend.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    public AccountService(UserRepository userRepository, PasswordEncoder passwordEncoder, ImageService imageService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.imageService = imageService;
    }

    public User getUser(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Користувача не знайдено"));
        return new User(user.getId(), user.getUsername(), user.getEmail(), user.getAvatarUrl());
    }

    @Transactional
    public UserEntity changeEmail(String currentEmail, String newEmail) {
        if (userRepository.existsByEmail(newEmail)) {
            throw new EmailAlreadyInUseException("Ця електронна пошта вже використовується іншим користувачем");
        }

        UserEntity user = getUserByEmail(currentEmail);
        user.setEmail(newEmail);
        return userRepository.save(user);
    }

    @Transactional
    public UserEntity changeUsername(String currentEmail, String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyInUseException("Це імʼя вже використовується іншим користувачем");
        }

        UserEntity user = getUserByEmail(currentEmail);
        user.setUsername(username);
        return userRepository.save(user);
    }

    @Transactional
    public void changePassword(String currentEmail, String oldPassword, String newPassword) {
        UserEntity user = getUserByEmail(currentEmail);

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new InvalidPasswordException("Поточний пароль неправильний");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Transactional
    public UserEntity changeAvatar(String currentEmail, String newAvatarUrl) {
        UserEntity user = getUserByEmail(currentEmail);
        String oldAvatarUrl = user.getAvatarUrl();

        if (oldAvatarUrl != null && !oldAvatarUrl.equals(newAvatarUrl)) {
            imageService.deleteImageByUrl(oldAvatarUrl);
        }

        user.setAvatarUrl(newAvatarUrl);
        return userRepository.save(user);
    }

    private UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Поточного користувача не знайдено"));
    }
}
