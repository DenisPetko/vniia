package com.github.testapp.service;


import com.github.testapp.dto.request.DetailRequestDto;
import com.github.testapp.dto.response.DetailResponseDto;

import java.util.Optional;
import java.util.UUID;

public interface DetailService {
    Optional<UUID> createNewDetail(Long documentId, DetailRequestDto dto);

    DetailResponseDto getById(UUID id);

    DetailResponseDto updateById(UUID id, DetailRequestDto dto);

    void deleteDetail(UUID id);
}
