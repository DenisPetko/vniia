package com.github.testapp.db.repository;

import com.github.testapp.db.entity.LogErrorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LogErrorRepository extends JpaRepository<LogErrorEntity, UUID> {
}
