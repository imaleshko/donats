package com.donats.backend.fundraiser.cards;

import com.donats.backend.fundraiser.FundraiserRepository;
import com.donats.backend.fundraiser.FundraiserStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FundraiserCardsService {

    private final FundraiserRepository fundraiserRepository;

    public FundraiserCardsService(FundraiserRepository fundraiserRepository) {
        this.fundraiserRepository = fundraiserRepository;
    }

    @Transactional(readOnly = true)
    public List<FundraiserCard> getNewest() {
        return fundraiserRepository.findTop5ByStatusOrderByStartedAtDesc(FundraiserStatus.ACTIVE)
                .stream()
                .map(FundraiserCard::from)
                .toList();
    }
}
