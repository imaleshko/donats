package com.donats.backend.fundraising;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FundraisingRepository extends JpaRepository<FundraisingEntity, Long> {
    @EntityGraph(attributePaths = {"user"})
    Optional<FundraisingEntity> findByUserUsernameAndSlug(String username, String slug);

    @EntityGraph(attributePaths = {"user"})
    List<FundraisingEntity> findTop5ByStatusOrderByStartedAtDesc(FundraisingStatus status);

    @EntityGraph(attributePaths = {"donations"})
    List<FundraisingEntity> findAllByUserEmailOrderByStartedAtDesc(String email);

    boolean existsByUserAndSlug(com.donats.backend.entities.UserEntity user, String slug);
}
