package com.donats.backend.update;

import com.donats.backend.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fundraising")
public class FundraisingUpdateController {
    private final FundraisingUpdateService updateService;

    public FundraisingUpdateController(FundraisingUpdateService updateService) {
        this.updateService = updateService;
    }

    @PostMapping("/{fundraisingId}/updates")
    public ResponseEntity<Void> createUpdate(
            @PathVariable Long fundraisingId,
            @Valid @RequestBody CreateFundraisingUpdateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        updateService.createFundraisingUpdate(userDetails.getUsername(), fundraisingId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
