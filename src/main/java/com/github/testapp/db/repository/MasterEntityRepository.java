package com.github.testapp.db.repository;

import com.github.testapp.db.entity.MasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MasterEntityRepository extends JpaRepository<MasterEntity, UUID> {
    Optional<MasterEntity> findByDocumentId(Long documentId);
}
