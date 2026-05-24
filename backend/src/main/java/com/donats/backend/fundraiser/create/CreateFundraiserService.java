package com.donats.backend.fundraiser.create;

import com.donats.backend.exceptions.SlugAlreadyInUseException;
import com.donats.backend.exceptions.UserNotFoundException;
import com.donats.backend.fundraiser.FundraiserEntity;
import com.donats.backend.fundraiser.FundraiserRepository;
import com.donats.backend.fundraiser.FundraiserStatus;
import com.donats.backend.fundraiser.tag.TagEntity;
import com.donats.backend.fundraiser.tag.TagRepository;
import com.donats.backend.user.UserEntity;
import com.donats.backend.user.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CreateFundraiserService {

    private final FundraiserRepository fundraiserRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    public CreateFundraiserService(FundraiserRepository fundraiserRepository, UserRepository userRepository, TagRepository tagRepository) {
        this.fundraiserRepository = fundraiserRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
    }

    @Transactional
    public void createFundraiser(Long userId, CreateFundraiserRequest request) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Користувача не знайдено"));

        if (fundraiserRepository.existsByUserIdAndSlug(user.getId(), request.slug())) {
            throw new SlugAlreadyInUseException("Slug '" + request.slug() + "' вже використовується у ваших зборах");
        }

        Set<TagEntity> tags = request.tags().stream()
                .map(tagName -> tagName.trim().toLowerCase())
                .map(tagName -> tagRepository.findByName(tagName).orElseGet(() -> tagRepository.save(createTag(tagName))))
                .collect(Collectors.toSet());

        FundraiserEntity fundraiser = createFundraiserEntity(request, user, tags);

        fundraiserRepository.save(fundraiser);
    }

    private static @NonNull TagEntity createTag(String name) {
        TagEntity tag = new TagEntity();
        tag.setName(name);
        return tag;
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
