package com.donats.backend.fundraiser.creating;

import com.donats.backend.entities.UserEntity;
import com.donats.backend.exceptions.UserNotFoundException;
import com.donats.backend.fundraiser.FundraiserEntity;
import com.donats.backend.fundraiser.FundraiserRepository;
import com.donats.backend.fundraiser.FundraiserStatus;
import com.donats.backend.fundraiser.creating.exception.SlugAlreadyInUseException;
import com.donats.backend.repositories.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FundraiserService {

    private final FundraiserRepository fundraiserRepository;
    private final UserRepository userRepository;

    public FundraiserService(FundraiserRepository fundraiserRepository, UserRepository userRepository) {
        this.fundraiserRepository = fundraiserRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createFundraiser(String email, CreateFundraiserRequest request) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Користувача не знайдено"));

        if (fundraiserRepository.existsByUserAndSlug(user, request.slug())) {
            throw new SlugAlreadyInUseException("Slug '" + request.slug() + "' вже використовується у ваших зборах");
        }

        FundraiserEntity fundraiser = getFundraiserEntity(request, user);

        fundraiserRepository.save(fundraiser);
    }

    private static @NonNull FundraiserEntity getFundraiserEntity(CreateFundraiserRequest request, UserEntity user) {
        FundraiserEntity fundraiser = new FundraiserEntity();
        fundraiser.setTitle(request.title());
        fundraiser.setSlug(request.slug());
        fundraiser.setDescription(request.description());
        fundraiser.setGoal(request.goal());
        fundraiser.setImageUrls(request.imagesUrl() != null ? request.imagesUrl() : List.of());

        fundraiser.setBalance(BigDecimal.ZERO);
        fundraiser.setStatus(FundraiserStatus.ACTIVE);
        fundraiser.setUser(user);
        return fundraiser;
    }
}
