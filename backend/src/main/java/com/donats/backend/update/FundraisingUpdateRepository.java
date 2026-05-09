package com.donats.backend.update;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FundraisingUpdateRepository extends JpaRepository<FundraisingUpdateEntity, Long> {
    List<FundraisingUpdateEntity> findAllByFundraisingIdOrderByCreatedAtDesc(Long fundraisingId);
}
