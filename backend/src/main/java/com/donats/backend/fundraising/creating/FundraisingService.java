package com.donats.backend.fundraising.creating;

import com.donats.backend.entities.UserEntity;
import com.donats.backend.exceptions.UserNotFoundException;
import com.donats.backend.fundraising.FundraisingEntity;
import com.donats.backend.fundraising.FundraisingRepository;
import com.donats.backend.fundraising.FundraisingStatus;
import com.donats.backend.fundraising.creating.dto.CreateFundraisingRequest;
import com.donats.backend.fundraising.creating.exception.SlugAlreadyInUseException;
import com.donats.backend.repositories.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FundraisingService {

    private final FundraisingRepository fundraisingRepository;
    private final UserRepository userRepository;

    public FundraisingService(FundraisingRepository fundraisingRepository, UserRepository userRepository) {
        this.fundraisingRepository = fundraisingRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createFundraising(String email, CreateFundraisingRequest request) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Користувача не знайдено"));

        if (fundraisingRepository.existsByUserAndSlug(user, request.slug())) {
            throw new SlugAlreadyInUseException("Slug '" + request.slug() + "' вже використовується у ваших зборах");
        }

        FundraisingEntity fundraising = getFundraisingEntity(request, user);

        fundraisingRepository.save(fundraising);
    }

    private static @NonNull FundraisingEntity getFundraisingEntity(CreateFundraisingRequest request, UserEntity user) {
        FundraisingEntity fundraising = new FundraisingEntity();
        fundraising.setTitle(request.title());
        fundraising.setSlug(request.slug());
        fundraising.setDescription(request.description());
        fundraising.setGoal(request.goal());
        fundraising.setEndDate(request.endDate());
        fundraising.setImageUrls(request.imagesUrl() != null ? request.imagesUrl() : List.of());

        fundraising.setBalance(BigDecimal.ZERO);
        fundraising.setStatus(FundraisingStatus.ACTIVE);
        fundraising.setUser(user);
        return fundraising;
    }
}
