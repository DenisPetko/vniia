package com.github.testapp.mapper;

import com.github.testapp.config.MapStructConfig;
import com.github.testapp.db.entity.DetailEntity;
import com.github.testapp.db.entity.MasterEntity;
import com.github.testapp.dto.request.DetailRequestDto;
import com.github.testapp.dto.request.MasterRequestDto;
import com.github.testapp.dto.response.MasterResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(config = MapStructConfig.class, uses = DetailMapper.class)
public interface MasterMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sumDetails", ignore = true)
    @Mapping(target = "details", expression = "java(mapDetails(dto))")
    MasterEntity toEntity(MasterRequestDto dto);

    MasterResponseDto toDto(MasterEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sumDetails", ignore = true)
    void updateEntityFromDto(MasterRequestDto dto, @MappingTarget MasterEntity entity);

    @Named("mapDetails")
    default List<DetailEntity> mapDetails(MasterRequestDto dto) {
        if (dto.getDetails() == null) {
            return null;
        }
        return dto.getDetails().stream()
                .map(detail -> {
                    DetailEntity entity = new DetailEntity();
                    entity.setDetailName(detail.getDetailName());
                    entity.setAmount(detail.getAmount());
                    entity.setMasterDocumentId(dto.getDocumentId());
                    return entity;
                })
                .collect(Collectors.toList());
    }
}
