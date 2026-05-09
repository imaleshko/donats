package com.donats.backend.donation;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DonationRepository extends JpaRepository<DonationEntity, Long> {
    Optional<DonationEntity> findByOrderId(String orderId);

    List<DonationEntity> findAllByFundraiserIdAndStatusOrderByCreatedAtDesc(Long fundraiserId, DonationStatusEnum status);

    @EntityGraph(attributePaths = {"fundraiser", "fundraiser.user"})
    List<DonationEntity> findAllByUserEmailAndStatusOrderByCreatedAtDesc(String email, DonationStatusEnum status);
}
