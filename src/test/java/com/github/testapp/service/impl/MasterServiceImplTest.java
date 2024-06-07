package com.github.testapp.service.impl;

import com.github.testapp.db.entity.MasterEntity;
import com.github.testapp.db.repository.MasterEntityRepository;
import com.github.testapp.dto.request.MasterRequestDto;
import com.github.testapp.exception.EntityAlreadyExistsException;
import com.github.testapp.mapper.MasterMapper;
import com.github.testapp.service.LogErrorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MasterServiceImplTest {
    @Mock
    private MasterEntityRepository masterEntityRepository;
    @Mock
    private MasterMapper masterMapper;
    @Mock
    private LogErrorService logErrorService;
    @InjectMocks
    private MasterServiceImpl masterService;
    private MasterRequestDto dto;
    private MasterEntity masterEntity;

    @BeforeEach
    void setUp() {
        dto = MasterRequestDto.builder()
                .documentId(111L)
                .date(LocalDate.now())
                .comment("test")
                .build();

        masterEntity = MasterEntity.builder()
                .id(UUID.randomUUID())
                .documentId(dto.getDocumentId())
                .date(dto.getDate())
                .comment(dto.getComment())
                .build();
    }

    @Test
    @DisplayName("Should save new entity.")
    void shouldCreateEntity() {
        when(masterEntityRepository.findByDocumentId(111L)).thenReturn(Optional.empty());
        when(masterMapper.toEntity(dto)).thenReturn(masterEntity);
        when(masterEntityRepository.save(masterMapper.toEntity(dto))).thenReturn(masterEntity);

        Optional<UUID> optionalUUID = masterService.createNewMaster(dto);

        assertTrue(optionalUUID.isPresent(), "Should return true.");
        assertEquals(optionalUUID.get(), masterEntity.getId(), "Should equals.");
    }

    @Test
    @DisplayName("Should create error message if entity already exist")
    void shouldError() {
        when(masterEntityRepository.findByDocumentId(111L)).thenReturn(Optional.of(masterEntity));
        doNothing().when(logErrorService).saveLogError("Document is already exists with id: " + dto.getDocumentId());

        EntityAlreadyExistsException e =
                assertThrows(EntityAlreadyExistsException.class,
                        () ->
                                masterService.createNewMaster(dto));
        assertEquals("Document is already exists with id: " + dto.getDocumentId(), e.getMessage());
    }
}