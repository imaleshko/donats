package com.donats.backend.update;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FundraiserUpdateRepository extends JpaRepository<FundraiserUpdateEntity, Long> {
    List<FundraiserUpdateEntity> findAllByFundraiserIdOrderByCreatedAtDesc(Long fundraiserId);
}
