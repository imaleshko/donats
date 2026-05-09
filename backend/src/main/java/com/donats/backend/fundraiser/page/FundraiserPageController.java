package com.donats.backend.fundraiser.page;

import com.donats.backend.donation.DonationService;
import com.donats.backend.donation.dto.DonationView;
import com.donats.backend.update.FundraiserUpdateService;
import com.donats.backend.update.dto.FundraiserUpdateView;
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
    private final FundraiserUpdateService updateService;
    private final DonationService donationService;

    public FundraiserPageController(
            FundraiserPageService fundraiserPageService,
            FundraiserUpdateService updateService,
            DonationService donationService) {
        this.fundraiserPageService = fundraiserPageService;
        this.updateService = updateService;
        this.donationService = donationService;
    }

    @GetMapping("/{username}/{slug}")
    public ResponseEntity<FundraiserResponse> getFundraiser(
            @PathVariable String username,
            @PathVariable String slug
    ) {
        return ResponseEntity.ok(fundraiserPageService.getFundraiserByUsernameAndSlug(username, slug));
    }


    @GetMapping("/{id}/updates")
    public ResponseEntity<List<FundraiserUpdateView>> getUpdates(@PathVariable Long id) {
        return ResponseEntity.ok(updateService.getFundraiserUpdates(id));
    }

    @GetMapping("/{id}/donations")
    public ResponseEntity<List<DonationView>> getDonations(@PathVariable Long id) {
        return ResponseEntity.ok(donationService.getSuccessfulDonations(id));
    }
}
