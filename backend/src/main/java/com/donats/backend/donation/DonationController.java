package com.donats.backend.donation;

import com.donats.backend.donation.dto.DonationInitRequest;
import com.donats.backend.donation.dto.DonationInitResponse;
import com.donats.backend.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/donations")
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @PostMapping("/init")
    public ResponseEntity<DonationInitResponse> initDonation(
            @Valid @RequestBody DonationInitRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails != null ? userDetails.getId() : null;
        return ResponseEntity.ok(donationService.initDonation(request, userId));
    }

    @PostMapping(value = "/liqpay/server", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> liqPayCallback(
            @RequestParam("data") String data,
            @RequestParam("signature") String signature
    ) {
        try {
            donationService.closeDonation(data, signature);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
