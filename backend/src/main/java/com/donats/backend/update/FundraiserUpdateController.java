package com.donats.backend.update;

import com.donats.backend.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fundraisers")
public class FundraiserUpdateController {
    private final FundraiserUpdateService updateService;

    public FundraiserUpdateController(FundraiserUpdateService updateService) {
        this.updateService = updateService;
    }

    @PostMapping("/{fundraiserId}/updates")
    public ResponseEntity<Void> createUpdate(
            @PathVariable Long fundraiserId,
            @Valid @RequestBody CreateFundraiserUpdateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        updateService.createFundraiserUpdate(userDetails.getUsername(), fundraiserId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
