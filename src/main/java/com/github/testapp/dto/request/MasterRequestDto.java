package com.github.testapp.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * Dto для входящих документов
 */
@Data
@Builder
public class MasterRequestDto {
    /**
     * Номер документа
     */
    @NotNull(message = "documentId cannot be null")
    private Long documentId;
    /**
     * Дата документа
     */
    @NotNull(message = "date cannot be null")
    private LocalDate date;
    /**
     * Примечание
     */
    private String comment;
    /**
     * Связанные с документом спецификации
     */
    private List<DetailRequestDto> details;
}
