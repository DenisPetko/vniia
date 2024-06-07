package com.github.testapp.config;

import org.mapstruct.*;

@MapperConfig(componentModel = MappingConstants.ComponentModel.SPRING,
unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MapStructConfig {}
