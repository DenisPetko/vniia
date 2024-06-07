package com.github.testapp.db.repository;

import com.github.testapp.db.entity.DetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DetailEntityRepository extends JpaRepository<DetailEntity, UUID> {
}
