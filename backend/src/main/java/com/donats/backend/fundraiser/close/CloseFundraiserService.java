package com.donats.backend.fundraiser.close;

import com.donats.backend.exceptions.ForbiddenException;
import com.donats.backend.exceptions.FundraiserNotFoundException;
import com.donats.backend.fundraiser.FundraiserEntity;
import com.donats.backend.fundraiser.FundraiserRepository;
import com.donats.backend.fundraiser.FundraiserStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CloseFundraiserService {

    private final FundraiserRepository fundraiserRepository;

    public CloseFundraiserService(FundraiserRepository fundraiserRepository) {
        this.fundraiserRepository = fundraiserRepository;
    }

    public void closeFundraiser(Long fundraiserId, Long userId) {
        FundraiserEntity fundraiser = fundraiserRepository.findById(fundraiserId)
                .orElseThrow(() -> new FundraiserNotFoundException("Збір не знайдено"));

        if (!fundraiser.getUser().getId().equals(userId)) {
            throw new ForbiddenException("Ви не маєте прав для закриття цього збору");
        }

        if (fundraiser.getStatus() == FundraiserStatus.CLOSED) {
            return;
        }

        fundraiser.setStatus(FundraiserStatus.CLOSED);
        fundraiser.setClosedAt(LocalDateTime.now());

        fundraiserRepository.save(fundraiser);
    }
}
