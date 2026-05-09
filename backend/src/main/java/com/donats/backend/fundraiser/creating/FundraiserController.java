package com.donats.backend.fundraiser.creating;

import com.donats.backend.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fundraisers")
public class FundraiserController {

    private final FundraiserService fundraiserService;

    public FundraiserController(FundraiserService fundraiserService) {
        this.fundraiserService = fundraiserService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createFundraiser(
            @Valid @RequestBody CreateFundraiserRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        fundraiserService.createFundraiser(userDetails.getUsername(), request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
