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

    private static final int PAGE_SIZE = 10;

    private final FundraiserRepository fundraiserRepository;

    public FundraiserCardsService(FundraiserRepository fundraiserRepository) {
        this.fundraiserRepository = fundraiserRepository;
    }

    @Transactional(readOnly = true)
    public FundraiserCardsPage getCards(int page, String tag) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);

        Slice<FundraiserEntity> result = (tag == null || tag.isBlank())
                ? fundraiserRepository.findByStatusOrderByStartedAtDesc(FundraiserStatus.ACTIVE, pageable)
                : fundraiserRepository.findByStatusAndTagsNameOrderByStartedAtDesc(FundraiserStatus.ACTIVE, tag.toLowerCase(), pageable);

        List<FundraiserCard> items = result.getContent().stream()
                .map(FundraiserCard::from)
                .toList();

        return new FundraiserCardsPage(items, result.hasNext());
    }
}
