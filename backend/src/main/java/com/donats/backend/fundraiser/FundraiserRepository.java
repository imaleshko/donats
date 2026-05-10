package com.donats.backend.fundraiser;

import com.donats.backend.user.UserEntity;
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

    boolean existsByUserAndSlug(UserEntity user, String slug);
}
