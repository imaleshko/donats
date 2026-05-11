package com.donats.backend.fundraiser.update;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UpdateRepository extends JpaRepository<UpdateEntity, Long> {
    List<UpdateEntity> findAllByFundraiserIdOrderByCreatedAtDesc(Long fundraiserId);
}
