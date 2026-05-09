package com.donats.backend.fundraising.editing;

import com.donats.backend.fundraising.editing.dto.EditFundraisingResponse;
import com.donats.backend.fundraising.editing.dto.UpdateFundraisingRequest;
import com.donats.backend.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fundraising/edit")
public class FundraisingEditingController {

    private final FundraisingEditingService editingService;

    public FundraisingEditingController(FundraisingEditingService editingService) {
        this.editingService = editingService;
    }

    @GetMapping("/{slug}")
    public ResponseEntity<EditFundraisingResponse> getFundraisingForEdit(
            @PathVariable String slug,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        EditFundraisingResponse response = editingService.getFundraisingForEdit(userDetails.getUsername(), slug);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{currentSlug}")
    public ResponseEntity<Void> updateFundraising(
            @PathVariable String currentSlug,
            @Valid @RequestBody UpdateFundraisingRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        editingService.updateFundraising(userDetails.getUsername(), currentSlug, request);
        return ResponseEntity.ok().build();
    }
}
