package com.donats.backend.fundraiser.editing;

import com.donats.backend.entities.UserEntity;
import com.donats.backend.exceptions.UserNotFoundException;
import com.donats.backend.fundraiser.FundraiserEntity;
import com.donats.backend.fundraiser.FundraiserRepository;
import com.donats.backend.fundraiser.creating.exception.SlugAlreadyInUseException;
import com.donats.backend.fundraiser.editing.dto.EditFundraiserResponse;
import com.donats.backend.fundraiser.editing.dto.UpdateFundraiserRequest;
import com.donats.backend.fundraiser.page.FundraiserNotFoundException;
import com.donats.backend.image.ImageService;
import com.donats.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FundraiserEditingService {

    private final FundraiserRepository fundraiserRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    public FundraiserEditingService(FundraiserRepository fundraiserRepository,
                                    UserRepository userRepository,
                                    ImageService imageService) {
        this.fundraiserRepository = fundraiserRepository;
        this.userRepository = userRepository;
        this.imageService = imageService;
    }

    @Transactional(readOnly = true)
    public EditFundraiserResponse getFundraiserForEdit(String email, String slug) {
        UserEntity user = getUserByEmail(email);

        FundraiserEntity fundraiser = fundraiserRepository.findByUserUsernameAndSlug(user.getUsername(), slug)
                .orElseThrow(() -> new FundraiserNotFoundException("Збір не знайдено"));

        return new EditFundraiserResponse(
                fundraiser.getId(),
                fundraiser.getTitle(),
                fundraiser.getSlug(),
                fundraiser.getDescription(),
                fundraiser.getGoal(),
                fundraiser.getImageUrls()
        );
    }

    @Transactional
    public void updateFundraiser(String email, String currentSlug,
                                 UpdateFundraiserRequest request) {
        UserEntity user = getUserByEmail(email);

        FundraiserEntity fundraiser = fundraiserRepository.findByUserUsernameAndSlug(user.getUsername(), currentSlug)
                .orElseThrow(() -> new FundraiserNotFoundException("Збір не знайдено"));

        if (!fundraiser.getSlug().equals(request.slug()) &&
                fundraiserRepository.existsByUserAndSlug(user, request.slug())) {
            throw new SlugAlreadyInUseException("Slug '" + request.slug() + "' вже використовується у ваших зборах");
        }

        List<String> currentImagesUrls = new ArrayList<>(fundraiser.getImageUrls());
        List<String> newImagesUrls = request.imagesUrl() != null ? request.imagesUrl() : List.of();

        List<String> urlsToDelete = currentImagesUrls.stream()
                .filter(url -> !newImagesUrls.contains(url))
                .toList();

        for (String url : urlsToDelete) {
            imageService.deleteImageByUrl(url);
        }

        fundraiser.setTitle(request.title());
        fundraiser.setSlug(request.slug());
        fundraiser.setDescription(request.description());
        fundraiser.setGoal(request.goal());
        fundraiser.setImageUrls(newImagesUrls);

        fundraiserRepository.save(fundraiser);
    }

    private UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Користувача не знайдено"));
    }
}
