package com.github.testapp.db.repository;

import com.github.testapp.db.entity.DetailEntity;
import com.github.testapp.db.entity.LogErrorEntity;
import com.github.testapp.db.entity.MasterEntity;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {
        "spring.test.database.replace=none",
        "spring.datasource.url=jdbc:tc:postgresql:16-alpine:///test"
})
class RepositoryTest {

    @Autowired
    MasterEntityRepository masterEntityRepository;
    @Autowired
    DetailEntityRepository detailEntityRepository;
    @Autowired
    LogErrorRepository logErrorRepository;

    @BeforeEach
    void setUp() {
        masterEntityRepository.deleteAll();
        detailEntityRepository.deleteAll();
        logErrorRepository.deleteAll();
        MasterEntity masterEntity1 = new MasterEntity(null, 111L, LocalDate.now(), "test", null, null);
        MasterEntity masterEntity2 = new MasterEntity(null, 222L, LocalDate.now(), "test", null, null);
        List<DetailEntity> detailEntities = List.of(
                new DetailEntity(null, "detail1", BigDecimal.valueOf(100), 111L),
                new DetailEntity(null, "detail2", BigDecimal.valueOf(200), 111L));
        masterEntity1.setDetails(detailEntities);
        LogErrorEntity logErrorEntity = new LogErrorEntity(null, "Test error message", LocalDateTime.now());

        masterEntityRepository.save(masterEntity1);
        masterEntityRepository.save(masterEntity2);
        logErrorRepository.save(logErrorEntity);
    }

    @Test
    void shouldGetMasterEntity() {
        List<MasterEntity> entityList = masterEntityRepository.findAll();
        assertEquals(2, entityList.size(), "Size should be 2.");
    }

    @Test
    void shouldGetDetailEntity() {
        List<DetailEntity> entityList = detailEntityRepository.findAll();
        assertEquals(2, entityList.size(), "Size should be 2.");
    }

    @Test
    void shouldGetLogErrorEntity() {
        List<LogErrorEntity> entityList = logErrorRepository.findAll();
        assertEquals(1, entityList.size(), "Size should be 1.");
    }
}