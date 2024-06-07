package com.github.testapp.controller;

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

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class MasterControllerTest {
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

    @BeforeEach
    void setUp() {
        masterEntityRepository.deleteAll();
        detailEntityRepository.deleteAll();
        logErrorRepository.deleteAll();

        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    void shouldCreateMasterEntitySuccessfully() {
        given()
                .contentType(ContentType.JSON)
                .body(
                        """
                                {
                                  "documentId": 12345,
                                  "date": "2024-06-02",
                                  "comment": "This is a sample document",
                                  "details": [
                                    {
                                      "detailName": "Sample Detail 1",
                                      "amount": 100.50
                                    },
                                    {
                                      "detailName": "Sample Detail 2",
                                      "amount": 200.75
                                    }
                                  ]
                                }
                                                        """
                )
                .when()
                .post("/api/master")
                .then()
                .statusCode(201);

        assertAll(
                () -> assertEquals(1, masterEntityRepository.findAll().size(), "Size should be 1"),
                () -> assertEquals(2, detailEntityRepository.findAll().size(), "Size should be 2"),
                () -> assertEquals(BigDecimal.valueOf(301.25), masterEntityRepository.findByDocumentId(12345L).get().getSumDetails())
        );
    }
}