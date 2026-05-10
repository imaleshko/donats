package com.donats.backend.fundraiser;

import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FundraiserRepository extends JpaRepository<FundraiserEntity, Long> {
    @EntityGraph(attributePaths = {"user"})
    Optional<FundraiserEntity> findByUserUsernameAndSlug(String username, String slug);

    @EntityGraph(attributePaths = {"user"})
    List<FundraiserEntity> findTop5ByStatusOrderByStartedAtDesc(FundraiserStatus status);

    @EntityGraph(attributePaths = {"user", "donations"})
    List<FundraiserEntity> findAllByUserIdOrderByStartedAtDesc(Long id);

    @EntityGraph(attributePaths = {"user"})
    @NonNull
    Optional<FundraiserEntity> findById(@NonNull Long id);

    boolean existsByUserIdAndSlug(Long userId, String slug);
}
