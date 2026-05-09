package com.donats.backend.update;

import com.donats.backend.entities.UserEntity;
import com.donats.backend.exceptions.UserNotFoundException;
import com.donats.backend.fundraising.FundraisingEntity;
import com.donats.backend.fundraising.FundraisingRepository;
import com.donats.backend.fundraising.page.FundraisingNotFoundException;
import com.donats.backend.repositories.UserRepository;
import com.donats.backend.update.dto.FundraisingUpdateView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FundraisingUpdateService {

    private final FundraisingUpdateRepository updateRepository;
    private final FundraisingRepository fundraisingRepository;
    private final UserRepository userRepository;

    public FundraisingUpdateService(FundraisingUpdateRepository updateRepository,
                                    FundraisingRepository fundraisingRepository,
                                    UserRepository userRepository) {
        this.updateRepository = updateRepository;
        this.fundraisingRepository = fundraisingRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createFundraisingUpdate(String email, Long fundraisingId, CreateFundraisingUpdateRequest request) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Користувача не знайдено"));

        FundraisingEntity fundraising = fundraisingRepository.findById(fundraisingId)
                .orElseThrow(() -> new FundraisingNotFoundException("Збір не знайдено"));

        if (!fundraising.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Ви не маєте прав додавати апдейти до цього збору");
        }

        FundraisingUpdateEntity update = new FundraisingUpdateEntity();
        update.setTitle(request.title());
        update.setMessage(request.message());
        update.setFundraising(fundraising);

        updateRepository.save(update);
    }

    @Transactional(readOnly = true)
    public List<FundraisingUpdateView> getFundraisingUpdates(Long fundraisingId) {
        return updateRepository.findAllByFundraisingIdOrderByCreatedAtDesc(fundraisingId)
                .stream()
                .map(FundraisingUpdateView::from)
                .toList();
    }
}
