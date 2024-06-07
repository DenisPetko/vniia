package com.github.testapp.controller;

import com.github.testapp.dto.request.MasterRequestDto;
import com.github.testapp.dto.response.MasterResponseDto;
import com.github.testapp.exception.EntityAlreadyExistsException;
import com.github.testapp.service.MasterService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/master")
@RequiredArgsConstructor
@Validated
public class MasterController {

    private final MasterService masterService;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody MasterRequestDto dto) {
        log.debug("Request create account: {}", dto);
        return masterService.createNewMaster(dto).isPresent()
                ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MasterResponseDto> getMasterById(@PathVariable @NotNull UUID id) {
        log.debug("Request get Master by Id: {}", id);
        return ResponseEntity.ok(masterService.getById(id));
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<MasterResponseDto> getMasterByDocumentId(@PathVariable @NotNull Long documentId) {
        log.debug("Request get Master by DocumentId: {}", documentId);
        return ResponseEntity.ok(masterService.getByDocumentId(documentId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MasterResponseDto> updateById(@PathVariable @NotNull UUID id, @Valid @RequestBody MasterRequestDto dto){
        log.debug("Request update Master by id: {}, {}", id, dto);
        return ResponseEntity.ok(masterService.updateById(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id){
        log.debug("Request delete Master by Id: {}", id);
        masterService.deleteMaster(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<?>  handleException(EntityAlreadyExistsException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
