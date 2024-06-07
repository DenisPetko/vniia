package com.github.testapp.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Dto для исходящих спецификаций
 */
@Data
@Builder
public class DetailResponseDto {
    /**
     * Идентификатор спецификации в базе данных
     */
    private UUID id;
    /**
     * Сумма спецификации
     */
    private BigDecimal amount;
    /**
     * Наименование спецификации
     */
    private String detailName;
}
