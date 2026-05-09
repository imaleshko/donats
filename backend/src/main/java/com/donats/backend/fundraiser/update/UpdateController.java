package com.donats.backend.fundraiser.update;

import com.donats.backend.fundraiser.update.dto.CreateUpdateRequest;
import com.donats.backend.fundraiser.update.dto.UpdateView;
import com.donats.backend.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fundraisers")
public class UpdateController {
    private final UpdateService updateService;

    public UpdateController(UpdateService updateService) {
        this.updateService = updateService;
    }

    @PostMapping("/{fundraiserId}/updates")
    public ResponseEntity<Void> createUpdate(
            @PathVariable Long fundraiserId,
            @Valid @RequestBody CreateUpdateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        updateService.createUpdate(fundraiserId, request, userDetails.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{fundraiserId}/updates")
    public ResponseEntity<List<UpdateView>> getUpdates(@PathVariable Long fundraiserId) {
        return ResponseEntity.ok(updateService.getUpdates(fundraiserId));
    }
}
