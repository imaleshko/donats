package com.donats.backend.fundraiser.tag;

import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Transactional(readOnly = true)
    public List<Tag> getTags() {
        return tagRepository.findAll().stream().map(Tag::from).toList();
    }

    @Transactional
    public Set<TagEntity> findOrCreateTags(Set<String> names) {
        return names.stream()
                .map(tagName -> tagName.trim().toLowerCase())
                .map(tagName -> tagRepository.findByName(tagName).orElseGet(() -> tagRepository.save(createTag(tagName))))
                .collect(Collectors.toSet());
    }

    private static @NonNull TagEntity createTag(String name) {
        TagEntity tag = new TagEntity();
        tag.setName(name);
        return tag;
    }
}
