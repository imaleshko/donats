package com.donats.backend.fundraiser.page;

import com.donats.backend.fundraiser.FundraiserEntity;
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
    public FundraiserResponse getFundraiserByUsernameAndSlug(String username, String slug) {
        FundraiserEntity fundraiser = fundraiserRepository.findByUserUsernameAndSlug(username, slug)
                .orElseThrow(() -> new FundraiserNotFoundException("Збір не знайдено"));

        return new FundraiserResponse(
                fundraiser.getId(),
                fundraiser.getTitle(),
                fundraiser.getSlug(),
                fundraiser.getDescription(),
                fundraiser.getBalance(),
                fundraiser.getGoal(),
                fundraiser.getImageUrls(),
                fundraiser.getUser().getUsername(),
                fundraiser.getUser().getAvatarUrl(),
                fundraiser.getStatus().name(),
                fundraiser.getStartedAt(),
                fundraiser.getEndedAt()
        );
    }
}
