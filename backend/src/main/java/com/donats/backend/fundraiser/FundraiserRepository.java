package com.donats.backend.fundraiser;

import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FundraiserRepository extends JpaRepository<FundraiserEntity, Long> {
    @EntityGraph(attributePaths = {"user", "imageUrls"})
    Optional<FundraiserEntity> findByUserUsernameAndSlug(String username, String slug);

    @EntityGraph(attributePaths = {"user", "tags"})
    Slice<FundraiserEntity> findByStatusOrderByStartedAtDesc(FundraiserStatus status, Pageable pageable);

    @EntityGraph(attributePaths = {"user", "tags"})
    Slice<FundraiserEntity> findByStatusAndTagsNameOrderByStartedAtDesc(FundraiserStatus status, String name, Pageable pageable);

    @EntityGraph(attributePaths = {"user", "donations"})
    List<FundraiserEntity> findAllByUserIdOrderByStartedAtDesc(Long id);

    @EntityGraph(attributePaths = {"tags", "user"})
    @NonNull
    Optional<FundraiserEntity> findById(@NonNull Long id);

    boolean existsByUserIdAndSlug(Long userId, String slug);
}
