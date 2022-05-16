package com.tracer.app.dto.mapper;

import com.tracer.app.domain.Feature;
import com.tracer.app.dto.FeatureDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FeatureValueMapper {
    FeatureValueMapper INSTANCE = Mappers.getMapper(FeatureValueMapper.class);

    FeatureDTO asFeatureDTO(Feature feature);

    Feature asFeature(FeatureDTO featureDTO);
}
