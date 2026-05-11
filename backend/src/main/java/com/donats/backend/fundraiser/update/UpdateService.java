package com.donats.backend.fundraiser.update;

import com.donats.backend.exceptions.ForbiddenException;
import com.donats.backend.fundraiser.FundraiserEntity;
import com.donats.backend.exceptions.FundraiserNotFoundException;
import com.donats.backend.fundraiser.FundraiserRepository;
import com.donats.backend.fundraiser.update.dto.CreateUpdateRequest;
import com.donats.backend.fundraiser.update.dto.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UpdateService {

    private final UpdateRepository updateRepository;
    private final FundraiserRepository fundraiserRepository;

    public UpdateService(UpdateRepository updateRepository,
                         FundraiserRepository fundraiserRepository) {
        this.updateRepository = updateRepository;
        this.fundraiserRepository = fundraiserRepository;
    }

    @Transactional
    public void createUpdate(Long fundraiserId, CreateUpdateRequest request, Long userId) {
        FundraiserEntity fundraiser = fundraiserRepository.findById(fundraiserId)
                .orElseThrow(() -> new FundraiserNotFoundException("Збір не знайдено"));

        if (!fundraiser.getUser().getId().equals(userId)) {
            throw new ForbiddenException("Ви не маєте прав додавати оновлення до цього збору");
        }

        UpdateEntity update = new UpdateEntity();
        update.setTitle(request.title());
        update.setMessage(request.message());
        update.setFundraiser(fundraiser);

        updateRepository.save(update);
    }

    @Transactional(readOnly = true)
    public List<Update> getUpdates(Long fundraiserId) {
        return updateRepository.findAllByFundraiserIdOrderByCreatedAtDesc(fundraiserId)
                .stream()
                .map(Update::from)
                .toList();
    }
}
