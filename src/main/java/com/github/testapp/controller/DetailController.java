package com.github.testapp.controller;

import com.github.testapp.dto.request.DetailRequestDto;
import com.github.testapp.dto.response.DetailResponseDto;
import com.github.testapp.service.DetailService;
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
@RequestMapping("/api/detail")
@RequiredArgsConstructor
@Validated
public class DetailController {

    private final DetailService detailService;

    /**
     * Метод для записи новой спецификации.
     * @param documentId - номер существующего в БД документа к которому будет привязана спецификация
     * @param dto - новая спецификация
     * @return - статус
     */
    @PostMapping("/{documentId}")
    public ResponseEntity<Void> create(@PathVariable @NotNull Long documentId,
                                       @Valid @RequestBody DetailRequestDto dto) {
        log.debug("Request create detail: {}, to document with id: {}", dto, documentId);
        return detailService.createNewDetail(documentId, dto).isPresent()
                ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<DetailResponseDto> getDetailById(@PathVariable @NotNull UUID id) {
        log.debug("Request get Detail by Id: {}", id);
        return ResponseEntity.ok(detailService.getById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<DetailResponseDto> updateById(@PathVariable @NotNull UUID id,
                                                        @Valid @RequestBody DetailRequestDto dto) {
        log.debug("Request update detail: {}, by id: {}", dto, id);
        return ResponseEntity.ok(detailService.updateById(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        log.debug("Request delete Detail by Id: {}", id);
        detailService.deleteDetail(id);
        return ResponseEntity.noContent().build();
    }
}
