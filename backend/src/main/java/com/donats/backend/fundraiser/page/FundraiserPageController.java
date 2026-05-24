package com.donats.backend.fundraiser.page;

import com.donats.backend.donation.DonationService;
import com.donats.backend.donation.dto.Donation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/fundraisers")
public class FundraiserPageController {

    private final FundraiserPageService fundraiserPageService;
    private final DonationService donationService;

    public FundraiserPageController(
            FundraiserPageService fundraiserPageService,
            DonationService donationService) {
        this.fundraiserPageService = fundraiserPageService;
        this.donationService = donationService;
    }

    @GetMapping("/{username}/{slug}")
    public ResponseEntity<Fundraiser> getFundraiser(
            @PathVariable String username,
            @PathVariable String slug
    ) {
        return ResponseEntity.ok(fundraiserPageService.getFundraiserByUsernameAndSlug(username, slug));
    }

    @GetMapping("/{fundraiserId}/donations")
    public ResponseEntity<List<Donation>> getDonations(@PathVariable Long fundraiserId) {
        return ResponseEntity.ok(donationService.getSuccessfulDonations(fundraiserId));
    }
}
