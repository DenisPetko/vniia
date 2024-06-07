package com.github.testapp.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Dto для входящих спецификаций
 */
@Data
@Builder
public class DetailRequestDto {

    /**
     * Наименование спецификации
     */
    @NotNull(message = "detailName cannot be null")
    private String detailName;
    /**
     * Сумма спецификации
     */
    @NotNull
    private BigDecimal amount;
}
