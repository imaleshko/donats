package com.donats.backend.user.account;

import com.donats.backend.donation.DonationRepository;
import com.donats.backend.donation.DonationStatus;
import com.donats.backend.exceptions.UserNotFoundException;
import com.donats.backend.fundraiser.FundraiserRepository;
import com.donats.backend.image.ImageService;
import com.donats.backend.user.UserEntity;
import com.donats.backend.user.UserRepository;
import com.donats.backend.user.account.dto.UserDonation;
import com.donats.backend.user.account.dto.UserFundraiser;
import com.donats.backend.user.account.exceptions.EmailAlreadyInUseException;
import com.donats.backend.user.account.exceptions.InvalidPasswordException;
import com.donats.backend.user.account.exceptions.UsernameAlreadyInUseException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {

    private final UserRepository userRepository;
    private final DonationRepository donationRepository;
    private final FundraiserRepository fundraiserRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    public AccountService(UserRepository userRepository, DonationRepository donationRepository, FundraiserRepository fundraiserRepository, PasswordEncoder passwordEncoder, ImageService imageService) {
        this.userRepository = userRepository;
        this.donationRepository = donationRepository;
        this.fundraiserRepository = fundraiserRepository;
        this.passwordEncoder = passwordEncoder;
        this.imageService = imageService;
    }

    @Transactional
    public UserEntity changeEmail(Long id, String newEmail) {
        if (userRepository.existsByEmail(newEmail)) {
            throw new EmailAlreadyInUseException("Ця електронна пошта вже використовується іншим користувачем");
        }

        UserEntity user = getUserById(id);
        user.setEmail(newEmail);
        return userRepository.save(user);
    }

    @Transactional
    public UserEntity changeUsername(Long id, String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyInUseException("Це імʼя вже використовується іншим користувачем");
        }

        UserEntity user = getUserById(id);
        user.setUsername(username);
        return userRepository.save(user);
    }

    @Transactional
    public void changePassword(Long id, String oldPassword, String newPassword) {
        UserEntity user = getUserById(id);

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new InvalidPasswordException("Поточний пароль неправильний");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Transactional
    public UserEntity changeAvatar(Long id, String newAvatarUrl) {
        UserEntity user = getUserById(id);
        String oldAvatarUrl = user.getAvatarUrl();

        if (oldAvatarUrl != null && !oldAvatarUrl.equals(newAvatarUrl)) {
            imageService.deleteImageByUrl(oldAvatarUrl);
        }

        user.setAvatarUrl(newAvatarUrl);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<UserDonation> getUserDonations(Long id, DonationStatus status) {
        return donationRepository
                .findAllByUserIdAndStatusOrderByCreatedAtDesc(id, status)
                .stream()
                .map(UserDonation::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UserFundraiser> getUserFundraisers(Long id) {
        return fundraiserRepository
                .findAllByUserIdOrderByStartedAtDesc(id)
                .stream()
                .map(UserFundraiser::from)
                .toList();
    }

    private UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Поточного користувача не знайдено"));
    }
}
