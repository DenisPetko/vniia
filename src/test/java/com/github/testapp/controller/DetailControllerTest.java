package com.github.testapp.controller;

import com.github.testapp.db.entity.DetailEntity;
import com.github.testapp.db.entity.MasterEntity;
import com.github.testapp.db.repository.DetailEntityRepository;
import com.github.testapp.db.repository.LogErrorRepository;
import com.github.testapp.db.repository.MasterEntityRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class DetailControllerTest {
    @LocalServerPort
    private Integer port;
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");
    @Autowired
    MasterEntityRepository masterEntityRepository;
    @Autowired
    DetailEntityRepository detailEntityRepository;
    @Autowired
    LogErrorRepository logErrorRepository;
    Long documentId;

    @BeforeEach
    void setUp() {
        masterEntityRepository.deleteAll();
        detailEntityRepository.deleteAll();
        logErrorRepository.deleteAll();

        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    void shouldCreateNewDetail() {
        documentId = 777L;
        masterEntityRepository.save(
                MasterEntity.builder()
                        .documentId(documentId)
                        .date(LocalDate.now())
                        .comment("Test document")
                        .details(List.of(
                                DetailEntity.builder()
                                        .detailName("TestDetail #1")
                                        .amount(BigDecimal.valueOf(100.01))
                                        .masterDocumentId(documentId)
                                        .build()
                        ))
                        .build()
        );

        given()
                .contentType(ContentType.JSON)
                .body(
                        """
                                    {
                                      "detailName": "TestDetail #2",
                                      "amount": 200.50
                                    }
                                """
                )
                .when()
                .post("/api/detail/{documentId}", documentId)
                .then()
                .statusCode(201);
        assertAll(
                () -> assertEquals(2, detailEntityRepository.findAll().size()),
                () -> assertEquals(BigDecimal.valueOf(100.01 + 200.50),
                        masterEntityRepository.findByDocumentId(documentId).get().getSumDetails())
        );
    }
}