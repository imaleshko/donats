package com.donats.backend.fundraiser.create;

import com.donats.backend.exceptions.SlugAlreadyInUseException;
import com.donats.backend.exceptions.UserNotFoundException;
import com.donats.backend.fundraiser.FundraiserEntity;
import com.donats.backend.fundraiser.FundraiserRepository;
import com.donats.backend.fundraiser.FundraiserStatus;
import com.donats.backend.user.UserEntity;
import com.donats.backend.user.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CreateFundraiserService {

    private final FundraiserRepository fundraiserRepository;
    private final UserRepository userRepository;

    public CreateFundraiserService(FundraiserRepository fundraiserRepository, UserRepository userRepository) {
        this.fundraiserRepository = fundraiserRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createFundraiser(Long userId, CreateFundraiserRequest request) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Користувача не знайдено"));

        if (fundraiserRepository.existsByUserIdAndSlug(user.getId(), request.slug())) {
            throw new SlugAlreadyInUseException("Slug '" + request.slug() + "' вже використовується у ваших зборах");
        }

        FundraiserEntity fundraiser = createFundraiserEntity(request, user);

        fundraiserRepository.save(fundraiser);
    }

    private static @NonNull FundraiserEntity createFundraiserEntity(CreateFundraiserRequest request, UserEntity user) {
        FundraiserEntity fundraiser = new FundraiserEntity();
        fundraiser.setTitle(request.title());
        fundraiser.setSlug(request.slug());
        fundraiser.setDescription(request.description());
        fundraiser.setGoal(request.goal());
        fundraiser.setImageUrls(request.imageUrls() != null ? request.imageUrls() : List.of());

        fundraiser.setBalance(BigDecimal.ZERO);
        fundraiser.setStatus(FundraiserStatus.ACTIVE);
        fundraiser.setUser(user);
        return fundraiser;
    }
}
