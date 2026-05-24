package com.donats.backend.fundraiser.cards;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fundraisers")
public class FundraiserCardsController {

    private final FundraiserCardsService fundraiserCardsService;

    public FundraiserCardsController(FundraiserCardsService fundraiserCardsService) {
        this.fundraiserCardsService = fundraiserCardsService;
    }

    @GetMapping("/list")
    public ResponseEntity<FundraiserCardsPage> getCards(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(fundraiserCardsService.getCards(page));
    }
}
