package com.github.testapp.mapper;

import com.github.testapp.config.MapStructConfig;
import com.github.testapp.db.entity.DetailEntity;
import com.github.testapp.db.entity.MasterEntity;
import com.github.testapp.dto.request.DetailRequestDto;
import com.github.testapp.dto.request.MasterRequestDto;
import com.github.testapp.dto.response.DetailResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface DetailMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "masterDocumentId", ignore = true)
    DetailEntity toEntity(DetailRequestDto dto);

    DetailResponseDto toDto(DetailEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "masterDocumentId", ignore = true)
    void updateEntityFromDto(DetailRequestDto dto, @MappingTarget DetailEntity entity);
}
