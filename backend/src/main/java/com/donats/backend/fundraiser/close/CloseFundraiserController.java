package com.donats.backend.fundraiser.close;

import com.donats.backend.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fundraisers")
public class CloseFundraiserController {

    private final CloseFundraiserService closeFundraiserService;

    public CloseFundraiserController(CloseFundraiserService closeFundraiserService) {
        this.closeFundraiserService = closeFundraiserService;
    }

    @PutMapping("/{fundraiserId}/close")
    public ResponseEntity<Void> closeFundraiser(
            @PathVariable Long fundraiserId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        closeFundraiserService.closeFundraiser(fundraiserId, userDetails.getId());
        return ResponseEntity.ok().build();
    }
}
