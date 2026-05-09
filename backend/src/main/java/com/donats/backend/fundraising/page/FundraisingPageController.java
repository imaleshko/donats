package com.donats.backend.fundraising.page;

import com.donats.backend.donation.DonationService;
import com.donats.backend.donation.dto.DonationView;
import com.donats.backend.update.FundraisingUpdateService;
import com.donats.backend.update.dto.FundraisingUpdateView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/fundraising")
public class FundraisingPageController {

    private final FundraisingPageService fundraisingPageService;
    private final FundraisingUpdateService updateService;
    private final DonationService donationService;

    public FundraisingPageController(
            FundraisingPageService fundraisingPageService,
            FundraisingUpdateService updateService,
            DonationService donationService) {
        this.fundraisingPageService = fundraisingPageService;
        this.updateService = updateService;
        this.donationService = donationService;
    }

    @GetMapping("/{username}/{slug}")
    public ResponseEntity<FundraisingResponse> getFundraising(
            @PathVariable String username,
            @PathVariable String slug
    ) {
        return ResponseEntity.ok(fundraisingPageService.getFundraisingByUsernameAndSlug(username, slug));
    }


    @GetMapping("/{id}/updates")
    public ResponseEntity<List<FundraisingUpdateView>> getUpdates(@PathVariable Long id) {
        return ResponseEntity.ok(updateService.getFundraisingUpdates(id));
    }

    @GetMapping("/{id}/donations")
    public ResponseEntity<List<DonationView>> getDonations(@PathVariable Long id) {
        return ResponseEntity.ok(donationService.getSuccessfulDonations(id));
    }
}
