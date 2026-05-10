package com.donats.backend.fundraiser.create;

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
public class CreateFundraiserController {

    private final CreateFundraiserService createFundraiserService;

    public CreateFundraiserController(CreateFundraiserService createFundraiserService) {
        this.createFundraiserService = createFundraiserService;
    }

    @PostMapping
    public ResponseEntity<Void> createFundraiser(
            @Valid @RequestBody CreateFundraiserRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        createFundraiserService.createFundraiser(userDetails.getId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
