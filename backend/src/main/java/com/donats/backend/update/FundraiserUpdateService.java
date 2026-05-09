package com.donats.backend.update;

import com.donats.backend.entities.UserEntity;
import com.donats.backend.exceptions.UserNotFoundException;
import com.donats.backend.fundraiser.FundraiserEntity;
import com.donats.backend.fundraiser.FundraiserRepository;
import com.donats.backend.fundraiser.page.FundraiserNotFoundException;
import com.donats.backend.repositories.UserRepository;
import com.donats.backend.update.dto.FundraiserUpdateView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FundraiserUpdateService {

    private final FundraiserUpdateRepository updateRepository;
    private final FundraiserRepository fundraiserRepository;
    private final UserRepository userRepository;

    public FundraiserUpdateService(FundraiserUpdateRepository updateRepository,
                                   FundraiserRepository fundraiserRepository,
                                   UserRepository userRepository) {
        this.updateRepository = updateRepository;
        this.fundraiserRepository = fundraiserRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createFundraiserUpdate(String email, Long fundraiserId, CreateFundraiserUpdateRequest request) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Користувача не знайдено"));

        FundraiserEntity fundraiser = fundraiserRepository.findById(fundraiserId)
                .orElseThrow(() -> new FundraiserNotFoundException("Збір не знайдено"));

        if (!fundraiser.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Ви не маєте прав додавати апдейти до цього збору");
        }

        FundraiserUpdateEntity update = new FundraiserUpdateEntity();
        update.setTitle(request.title());
        update.setMessage(request.message());
        update.setFundraiser(fundraiser);

        updateRepository.save(update);
    }

    @Transactional(readOnly = true)
    public List<FundraiserUpdateView> getFundraiserUpdates(Long fundraiserId) {
        return updateRepository.findAllByFundraiserIdOrderByCreatedAtDesc(fundraiserId)
                .stream()
                .map(FundraiserUpdateView::from)
                .toList();
    }
}
