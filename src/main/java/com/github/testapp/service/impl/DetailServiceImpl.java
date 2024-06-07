package com.github.testapp.service.impl;

import com.github.testapp.db.entity.DetailEntity;
import com.github.testapp.db.repository.DetailEntityRepository;
import com.github.testapp.dto.request.DetailRequestDto;
import com.github.testapp.dto.response.DetailResponseDto;
import com.github.testapp.dto.response.MasterResponseDto;
import com.github.testapp.exception.NotFoundException;
import com.github.testapp.mapper.DetailMapper;
import com.github.testapp.service.DetailService;
import com.github.testapp.service.MasterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DetailServiceImpl implements DetailService {

    private final DetailEntityRepository detailEntityRepository;
    private final MasterService masterService;
    private final DetailMapper detailMapper;

    @Override
    @Transactional
    public Optional<UUID> createNewDetail(Long documentId, DetailRequestDto dto) {
        MasterResponseDto master = masterService.getByDocumentId(documentId);
        DetailEntity detailEntity = detailMapper.toEntity(dto);
        detailEntity.setMasterDocumentId(master.getDocumentId());
        return Optional.of(detailEntityRepository.save(detailEntity).getId());
    }

    @Override
    public DetailResponseDto getById(UUID id) {
        return detailEntityRepository.findById(id)
                .map(detailMapper::toDto)
                .orElseThrow(
                        () -> new NotFoundException("Detail is not found with id: " + id)
                );
    }

    @Override
    @Transactional
    public DetailResponseDto updateById(UUID id, DetailRequestDto dto) {
        DetailEntity entity = detailEntityRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Detail is not found with id: " + id)
        );
        detailMapper.updateEntityFromDto(dto, entity);
        detailEntityRepository.save(entity);
        return detailMapper.toDto(entity);
    }

    @Override
    @Transactional
    public void deleteDetail(UUID id) {
        DetailEntity entity = detailEntityRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Detail is not found with id: " + id)
        );
        detailEntityRepository.delete(entity);
    }
}
