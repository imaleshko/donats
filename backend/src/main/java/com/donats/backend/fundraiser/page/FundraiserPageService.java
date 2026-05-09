package com.donats.backend.fundraiser.page;

import com.donats.backend.fundraiser.FundraiserNotFoundException;
import com.donats.backend.fundraiser.FundraiserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FundraiserPageService {

    private final FundraiserRepository fundraiserRepository;

    public FundraiserPageService(FundraiserRepository fundraiserRepository) {
        this.fundraiserRepository = fundraiserRepository;
    }

    @Transactional(readOnly = true)
    public Fundraiser getFundraiserByUsernameAndSlug(String username, String slug) {
        return fundraiserRepository.findByUserUsernameAndSlug(username, slug)
                .map(Fundraiser::from)
                .orElseThrow(() -> new FundraiserNotFoundException("Збір не знайдено"));
    }
}
