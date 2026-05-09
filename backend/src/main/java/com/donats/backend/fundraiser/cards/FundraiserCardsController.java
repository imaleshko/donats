package com.donats.backend.fundraiser.cards;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/fundraisers")
public class FundraiserCardsController {

    private final FundraiserCardsService fundraiserCardsService;

    public FundraiserCardsController(FundraiserCardsService fundraiserCardsService) {
        this.fundraiserCardsService = fundraiserCardsService;
    }

    @GetMapping("/newest")
    public ResponseEntity<List<FundraiserCard>> getNewest() {
        return ResponseEntity.ok(fundraiserCardsService.getNewest());
    }
}
