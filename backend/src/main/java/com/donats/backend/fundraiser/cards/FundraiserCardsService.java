package com.donats.backend.fundraiser.cards;

import com.donats.backend.fundraiser.FundraiserEntity;
import com.donats.backend.fundraiser.FundraiserRepository;
import com.donats.backend.fundraiser.FundraiserStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FundraiserCardsService {

    private final FundraiserRepository fundraiserRepository;
    private static final int PAGE_SIZE = 10;

    public FundraiserCardsService(FundraiserRepository fundraiserRepository) {
        this.fundraiserRepository = fundraiserRepository;
    }

    @Transactional(readOnly = true)
    public FundraiserCardsPage getCards(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);

        Slice<FundraiserEntity> result = fundraiserRepository.findByStatusOrderByStartedAtDesc(FundraiserStatus.ACTIVE, pageable);

        List<FundraiserCard> items = result.getContent().stream()
                .map(FundraiserCard::from)
                .toList();

        return new FundraiserCardsPage(items, result.hasNext());
    }
}
