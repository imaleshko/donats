package com.donats.backend.account;

import com.donats.backend.account.dto.*;
import com.donats.backend.donation.DonationEntity;
import com.donats.backend.donation.DonationRepository;
import com.donats.backend.donation.DonationStatusEnum;
import com.donats.backend.donation.dto.UserDonationResponse;
import com.donats.backend.entities.UserEntity;
import com.donats.backend.fundraiser.FundraiserEntity;
import com.donats.backend.fundraiser.FundraiserRepository;
import com.donats.backend.security.AccessTokenService;
import com.donats.backend.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;
    private final AccessTokenService accessTokenService;
    private final DonationRepository donationRepository;
    private final FundraiserRepository fundraiserRepository;

    public AccountController(AccountService accountService,
                             AccessTokenService accessTokenService,
                             DonationRepository donationRepository,
                             FundraiserRepository fundraiserRepository) {
        this.accountService = accountService;
        this.accessTokenService = accessTokenService;
        this.donationRepository = donationRepository;
        this.fundraiserRepository = fundraiserRepository;
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User profile = accountService.getUser(userDetails.getUsername());
        return ResponseEntity.ok(profile);
    }


    @PatchMapping("/email")
    public ResponseEntity<ChangeEmailResponse> changeEmail(
            @RequestBody ChangeEmailRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        UserEntity updatedUser = accountService.changeEmail(userDetails.getUsername(), request.email());

        String accessToken = accessTokenService.generateAccessToken(updatedUser.getEmail());

        User user = toUserDto(updatedUser);
        return ResponseEntity.ok(new ChangeEmailResponse(user, accessToken));
    }

    @PatchMapping("/username")
    public ResponseEntity<User> changeUsername(
            @RequestBody ChangeUsernameRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        UserEntity updatedUser = accountService.changeUsername(userDetails.getUsername(), request.username());

        return ResponseEntity.ok(toUserDto(updatedUser));
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(
            @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        accountService.changePassword(userDetails.getUsername(), request.oldPassword(), request.newPassword());

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/avatar")
    public ResponseEntity<User> changeAvatar(
            @RequestBody ChangeAvatarRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        UserEntity updatedUser = accountService.changeAvatar(userDetails.getUsername(), request.avatarUrl());

        return ResponseEntity.ok(toUserDto(updatedUser));
    }

    private User toUserDto(UserEntity user) {
        return new User(user.getId(), user.getUsername(), user.getEmail(), user.getAvatarUrl());
    }

    @GetMapping("/donations")
    public ResponseEntity<List<UserDonationResponse>> getMyDonations(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<UserDonationResponse> donations = donationRepository
                .findAllByUserEmailAndStatusOrderByCreatedAtDesc(userDetails.getUsername(), DonationStatusEnum.SUCCESS)
                .stream()
                .map(this::toUserUserDonationResponseDto)
                .toList();

        return ResponseEntity.ok(donations);
    }

    private UserDonationResponse toUserUserDonationResponseDto(DonationEntity donation) {
        return new UserDonationResponse(donation.getId(),
                donation.getName(),
                donation.getAmount(),
                donation.getCreatedAt(),
                donation.getMessage(),
                donation.getFundraiser().getTitle(),
                donation.getFundraiser().getSlug(),
                donation.getFundraiser().getUser().getUsername());
    }

    @GetMapping("/fundraisers")
    public ResponseEntity<List<UsersFundraiserResponse>> getUserFundraisers(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<UsersFundraiserResponse> fundraiser = fundraiserRepository
                .findAllByUserEmailOrderByStartedAtDesc(userDetails.getUsername())
                .stream()
                .map(this::toUsersFundraiserResponseDto)
                .toList();
        return ResponseEntity.ok(fundraiser);
    }

    private UsersFundraiserResponse toUsersFundraiserResponseDto(FundraiserEntity fundraiser) {
        long totalDonations = fundraiser.getDonations().stream().filter(donation -> donation.getStatus() == DonationStatusEnum.SUCCESS).count();

        return new UsersFundraiserResponse(
                fundraiser.getId(),
                fundraiser.getTitle(),
                fundraiser.getSlug(),
                fundraiser.getUser().getUsername(),
                fundraiser.getStartedAt(),
                fundraiser.getStatus(),
                fundraiser.getBalance(),
                totalDonations
        );
    }
}
