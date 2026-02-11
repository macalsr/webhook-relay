package com.mariaribeiro.webhookrelay.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataEventJpaRepository extends JpaRepository<EventJpaEntity, UUID> {
    Optional<EventJpaEntity> findBySourceAndEventKey(String source, String eventKey);
}