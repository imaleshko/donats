package com.donats.backend.fundraiser.edit;

import com.donats.backend.exceptions.FundraiserNotFoundException;
import com.donats.backend.exceptions.SlugAlreadyInUseException;
import com.donats.backend.fundraiser.FundraiserEntity;
import com.donats.backend.fundraiser.FundraiserRepository;
import com.donats.backend.fundraiser.edit.dto.EditFundraiserRequest;
import com.donats.backend.fundraiser.edit.dto.EditFundraiserResponse;
import com.donats.backend.fundraiser.tag.TagEntity;
import com.donats.backend.fundraiser.tag.TagService;
import com.donats.backend.image.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class EditFundraiserService {

    private final FundraiserRepository fundraiserRepository;
    private final ImageService imageService;
    private final TagService tagService;

    public EditFundraiserService(FundraiserRepository fundraiserRepository,
                                 ImageService imageService,
                                 TagService tagService) {
        this.fundraiserRepository = fundraiserRepository;
        this.imageService = imageService;
        this.tagService = tagService;
    }

    @Transactional(readOnly = true)
    public EditFundraiserResponse getFundraiserForEdit(Long userId, Long fundraiserId) {
        FundraiserEntity fundraiser = getFundraiser(userId, fundraiserId);
        return EditFundraiserResponse.from(fundraiser);
    }

    @Transactional
    public void editFundraiser(Long userId, Long fundraiserId, EditFundraiserRequest request) {
        FundraiserEntity fundraiser = getFundraiser(userId, fundraiserId);

        if (!fundraiser.getSlug().equals(request.slug()) &&
                fundraiserRepository.existsByUserIdAndSlug(userId, request.slug())) {
            throw new SlugAlreadyInUseException(
                    "Slug '" + request.slug() + "' вже використовується у ваших зборах"
            );
        }

        List<String> currentImagesUrls = new ArrayList<>(fundraiser.getImageUrls());
        List<String> newImagesUrls = request.imageUrls() != null ? request.imageUrls() : List.of();

        List<String> urlsToDelete = currentImagesUrls.stream()
                .filter(url -> !newImagesUrls.contains(url))
                .toList();

        for (String url : urlsToDelete) {
            imageService.deleteImageByUrl(url);
        }

        Set<TagEntity> tags = tagService.findOrCreateTags(request.tags());

        fundraiser.setTitle(request.title());
        fundraiser.setSlug(request.slug());
        fundraiser.setDescription(request.description());
        fundraiser.setGoal(request.goal());
        fundraiser.setImageUrls(newImagesUrls);
        fundraiser.setTags(tags);

        fundraiserRepository.save(fundraiser);
    }

    private FundraiserEntity getFundraiser(Long userId, Long fundraiserId) {
        FundraiserEntity fundraiser = fundraiserRepository.findById(fundraiserId)
                .orElseThrow(() -> new FundraiserNotFoundException("Збір не знайдено"));

        if (!fundraiser.getUser().getId().equals(userId)) {
            throw new FundraiserNotFoundException("Збір не знайдено");
        }

        return fundraiser;
    }
}
