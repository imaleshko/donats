package com.donats.backend.fundraiser.editing;

import com.donats.backend.fundraiser.editing.dto.EditFundraiserResponse;
import com.donats.backend.fundraiser.editing.dto.UpdateFundraiserRequest;
import com.donats.backend.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fundraisers/edit")
public class FundraiserEditingController {

    private final FundraiserEditingService editingService;

    public FundraiserEditingController(FundraiserEditingService editingService) {
        this.editingService = editingService;
    }

    @GetMapping("/{slug}")
    public ResponseEntity<EditFundraiserResponse> getFundraiserForEdit(
            @PathVariable String slug,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        EditFundraiserResponse response = editingService.getFundraiserForEdit(userDetails.getUsername(), slug);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{currentSlug}")
    public ResponseEntity<Void> updateFundraiser(
            @PathVariable String currentSlug,
            @Valid @RequestBody UpdateFundraiserRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        editingService.updateFundraiser(userDetails.getUsername(), currentSlug, request);
        return ResponseEntity.ok().build();
    }
}
