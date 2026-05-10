package com.donats.backend.user.account;

import com.donats.backend.donation.DonationStatus;
import com.donats.backend.security.AccessTokenService;
import com.donats.backend.security.CustomUserDetails;
import com.donats.backend.user.User;
import com.donats.backend.user.UserEntity;
import com.donats.backend.user.account.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/account")
public class AccountController {

    private final AccountService accountService;
    private final AccessTokenService accessTokenService;

    public AccountController(AccountService accountService,
                             AccessTokenService accessTokenService) {
        this.accountService = accountService;
        this.accessTokenService = accessTokenService;
    }

    @PatchMapping("/email")
    public ResponseEntity<ChangeEmailResponse> changeEmail(
            @RequestBody @Valid ChangeEmailRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        UserEntity updatedUser = accountService.changeEmail(userDetails.getId(), request.email());
        String accessToken = accessTokenService.generateAccessToken(updatedUser.getEmail());

        ChangeEmailResponse response = new ChangeEmailResponse(User.from(updatedUser), accessToken);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/username")
    public ResponseEntity<User> changeUsername(
            @RequestBody @Valid ChangeUsernameRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        UserEntity updatedUser = accountService.changeUsername(userDetails.getId(), request.username());

        return ResponseEntity.ok(User.from(updatedUser));
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(
            @RequestBody @Valid ChangePasswordRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        accountService.changePassword(userDetails.getId(), request.oldPassword(), request.newPassword());

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/avatar")
    public ResponseEntity<User> changeAvatar(
            @RequestBody @Valid ChangeAvatarRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        UserEntity updatedUser = accountService.changeAvatar(userDetails.getId(), request.avatarUrl());

        return ResponseEntity.ok(User.from(updatedUser));
    }

    @GetMapping("/donations")
    public ResponseEntity<List<UserDonation>> getUserDonations(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<UserDonation> donations = accountService.getUserDonations(userDetails.getId(), DonationStatus.SUCCESS);

        return ResponseEntity.ok(donations);
    }

    @GetMapping("/fundraisers")
    public ResponseEntity<List<UserFundraiser>> getUserFundraisers(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<UserFundraiser> fundraisers = accountService.getUserFundraisers(userDetails.getId());
        return ResponseEntity.ok(fundraisers);
    }
}
