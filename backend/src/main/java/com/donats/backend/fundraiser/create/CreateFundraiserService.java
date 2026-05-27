package com.donats.backend.fundraiser.create;

import com.donats.backend.exceptions.SlugAlreadyInUseException;
import com.donats.backend.exceptions.UserNotFoundException;
import com.donats.backend.fundraiser.FundraiserEntity;
import com.donats.backend.fundraiser.FundraiserRepository;
import com.donats.backend.fundraiser.FundraiserStatus;
import com.donats.backend.fundraiser.tag.TagEntity;
import com.donats.backend.fundraiser.tag.TagService;
import com.donats.backend.user.UserEntity;
import com.donats.backend.user.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;

@Service
public class CreateFundraiserService {

    private final FundraiserRepository fundraiserRepository;
    private final UserRepository userRepository;
    private final TagService tagService;

    public CreateFundraiserService(FundraiserRepository fundraiserRepository, UserRepository userRepository, TagService tagService) {
        this.fundraiserRepository = fundraiserRepository;
        this.userRepository = userRepository;
        this.tagService = tagService;
    }

    @Transactional
    public void createFundraiser(Long userId, CreateFundraiserRequest request) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Користувача не знайдено"));

        if (fundraiserRepository.existsByUserIdAndSlug(user.getId(), request.slug())) {
            throw new SlugAlreadyInUseException("Slug '" + request.slug() + "' вже використовується у ваших зборах");
        }

        Set<TagEntity> tags = tagService.findOrCreateTags(request.tags());
        FundraiserEntity fundraiser = createFundraiserEntity(request, user, tags);

        fundraiserRepository.save(fundraiser);
    }

    private static @NonNull FundraiserEntity createFundraiserEntity(CreateFundraiserRequest request, UserEntity user, Set<TagEntity> tags) {
        FundraiserEntity fundraiser = new FundraiserEntity();
        fundraiser.setTitle(request.title());
        fundraiser.setSlug(request.slug());
        fundraiser.setDescription(request.description());
        fundraiser.setGoal(request.goal());
        fundraiser.setImageUrls(request.imageUrls());
        fundraiser.setTags(tags);

        fundraiser.setBalance(BigDecimal.ZERO);
        fundraiser.setStatus(FundraiserStatus.ACTIVE);
        fundraiser.setUser(user);
        return fundraiser;
    }
}
