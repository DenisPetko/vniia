package com.github.testapp.service;

import com.github.testapp.dto.request.MasterRequestDto;
import com.github.testapp.dto.response.MasterResponseDto;

import java.util.Optional;
import java.util.UUID;

public interface MasterService {
    Optional<UUID> createNewMaster(MasterRequestDto dto);

    MasterResponseDto getById(UUID id);

    MasterResponseDto getByDocumentId(Long documentId);

    MasterResponseDto updateById(UUID id, MasterRequestDto dto);

    void deleteMaster(UUID id);
}
