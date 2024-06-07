package com.github.testapp.service.impl;

import com.github.testapp.db.entity.MasterEntity;
import com.github.testapp.db.repository.MasterEntityRepository;
import com.github.testapp.dto.request.MasterRequestDto;
import com.github.testapp.dto.response.MasterResponseDto;
import com.github.testapp.exception.EntityAlreadyExistsException;
import com.github.testapp.exception.NotFoundException;
import com.github.testapp.mapper.MasterMapper;
import com.github.testapp.service.LogErrorService;
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
public class MasterServiceImpl implements MasterService {

    private final MasterEntityRepository masterEntityRepository;
    private final MasterMapper masterMapper;
    private final LogErrorService logErrorService;

    @Override
    @Transactional
    public Optional<UUID> createNewMaster(MasterRequestDto dto) {
        if (masterEntityRepository.findByDocumentId(dto.getDocumentId()).isPresent()) {
            log.error("Document already exist, id: {}", dto.getDocumentId());
            logErrorService.saveLogError("Document is already exists with id: " + dto.getDocumentId());
            throw new EntityAlreadyExistsException("Document is already exists with id: " + dto.getDocumentId());
        }
        return Optional.of(masterEntityRepository.save(masterMapper.toEntity(dto)).getId());
    }

    @Override
    public MasterResponseDto getById(UUID id) {
        return masterEntityRepository.findById(id)
                .map(masterMapper::toDto)
                .orElseThrow(
                        () -> new NotFoundException("Document is not found with id: " + id)
                );
    }

    @Override
    public MasterResponseDto getByDocumentId(Long documentId) {
        return masterEntityRepository.findByDocumentId(documentId)
                .map(masterMapper::toDto)
                .orElseThrow(
                        () -> new NotFoundException("Document is not found with id: " + documentId)
                );
    }

    @Override
    @Transactional
    public MasterResponseDto updateById(UUID id, MasterRequestDto dto) {
        MasterEntity entity = masterEntityRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Document is not found with id: " + id)
        );
        masterMapper.updateEntityFromDto(dto, entity);
        masterEntityRepository.save(entity);
        return masterMapper.toDto(entity);
    }

    @Override
    @Transactional
    public void deleteMaster(UUID id) {
        MasterEntity entity = masterEntityRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Document is not found with id: " + id)
        );
        masterEntityRepository.delete(entity);
    }

}
