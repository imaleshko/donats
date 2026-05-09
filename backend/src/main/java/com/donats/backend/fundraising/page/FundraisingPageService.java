package com.donats.backend.fundraising.page;

import com.donats.backend.fundraising.FundraisingEntity;
import com.donats.backend.fundraising.FundraisingRepository;
import com.donats.backend.fundraising.page.dto.FundraisingResponseDto;
import com.donats.backend.fundraising.page.exception.FundraisingNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FundraisingPageService {

    private final FundraisingRepository fundraisingRepository;

    public FundraisingPageService(FundraisingRepository fundraisingRepository) {
        this.fundraisingRepository = fundraisingRepository;
    }

    @Transactional(readOnly = true)
    public FundraisingResponseDto getFundraisingByUsernameAndSlug(String username, String slug) {
        FundraisingEntity fundraising = fundraisingRepository.findByUserUsernameAndSlug(username, slug)
                .orElseThrow(() -> new FundraisingNotFoundException("Fundraising not found"));

        return new FundraisingResponseDto(
                fundraising.getId(),
                fundraising.getTitle(),
                fundraising.getSlug(),
                fundraising.getDescription(),
                fundraising.getBalance(),
                fundraising.getGoal(),
                fundraising.getEndDate(),
                fundraising.getImageUrls(),
                fundraising.getUser().getUsername(),
                fundraising.getUser().getAvatarUrl(),
                fundraising.getStatus().name(),
                fundraising.getStartedAt(),
                fundraising.getEndedAt()
        );
    }
}
