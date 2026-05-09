package com.donats.backend.fundraising.creating;

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
@RequestMapping("/api/fundraising")
public class FundraisingController {

    private final FundraisingService fundraisingService;

    public FundraisingController(FundraisingService fundraisingService) {
        this.fundraisingService = fundraisingService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createFundraising(
            @Valid @RequestBody CreateFundraisingRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        fundraisingService.createFundraising(userDetails.getUsername(), request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
