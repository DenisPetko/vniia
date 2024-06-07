package com.github.testapp.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Dto для исходящих документов
 */
@Data
@Builder
public class MasterResponseDto {
    /**
     * Идентификатор документа в базе данных
     */
    private UUID id;
    /**
     * Номер документа
     */
    private Long documentId;
    /**
     * Дата документа
     */
    private LocalDate date;
    /**
     * Сумма всех спецификаций относящихся к документу
     */
    private BigDecimal sumDetails;
    /**
     * Примечание
     */
    private String comment;
}
