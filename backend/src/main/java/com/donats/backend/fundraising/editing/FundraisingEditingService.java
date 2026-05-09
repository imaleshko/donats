package com.donats.backend.fundraising.editing;

import com.donats.backend.entities.UserEntity;
import com.donats.backend.exceptions.UserNotFoundException;
import com.donats.backend.fundraising.FundraisingEntity;
import com.donats.backend.fundraising.FundraisingRepository;
import com.donats.backend.fundraising.creating.exception.SlugAlreadyInUseException;
import com.donats.backend.fundraising.editing.dto.EditFundraisingResponse;
import com.donats.backend.fundraising.editing.dto.UpdateFundraisingRequest;
import com.donats.backend.fundraising.page.FundraisingNotFoundException;
import com.donats.backend.image.ImageService;
import com.donats.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FundraisingEditingService {

    private final FundraisingRepository fundraisingRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    public FundraisingEditingService(FundraisingRepository fundraisingRepository,
                                     UserRepository userRepository,
                                     ImageService imageService) {
        this.fundraisingRepository = fundraisingRepository;
        this.userRepository = userRepository;
        this.imageService = imageService;
    }

    @Transactional(readOnly = true)
    public EditFundraisingResponse getFundraisingForEdit(String email, String slug) {
        UserEntity user = getUserByEmail(email);

        FundraisingEntity fundraising = fundraisingRepository.findByUserUsernameAndSlug(user.getUsername(), slug)
                .orElseThrow(() -> new FundraisingNotFoundException("Збір не знайдено"));

        return new EditFundraisingResponse(
                fundraising.getId(),
                fundraising.getTitle(),
                fundraising.getSlug(),
                fundraising.getDescription(),
                fundraising.getGoal(),
                fundraising.getImageUrls()
        );
    }

    @Transactional
    public void updateFundraising(String email, String currentSlug,
                                  UpdateFundraisingRequest request) {
        UserEntity user = getUserByEmail(email);

        FundraisingEntity fundraising = fundraisingRepository.findByUserUsernameAndSlug(user.getUsername(), currentSlug)
                .orElseThrow(() -> new FundraisingNotFoundException("Збір не знайдено"));

        if (!fundraising.getSlug().equals(request.slug()) &&
                fundraisingRepository.existsByUserAndSlug(user, request.slug())) {
            throw new SlugAlreadyInUseException("Slug '" + request.slug() + "' вже використовується у ваших зборах");
        }

        List<String> currentImagesUrls = new ArrayList<>(fundraising.getImageUrls());
        List<String> newImagesUrls = request.imagesUrl() != null ? request.imagesUrl() : List.of();

        List<String> urlsToDelete = currentImagesUrls.stream()
                .filter(url -> !newImagesUrls.contains(url))
                .toList();

        for (String url : urlsToDelete) {
            imageService.deleteImageByUrl(url);
        }

        fundraising.setTitle(request.title());
        fundraising.setSlug(request.slug());
        fundraising.setDescription(request.description());
        fundraising.setGoal(request.goal());
        fundraising.setImageUrls(newImagesUrls);

        fundraisingRepository.save(fundraising);
    }

    private UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Користувача не знайдено"));
    }
}
