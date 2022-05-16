package com.tracer.app.dto.mapper;

import com.tracer.app.domain.Plan;
import com.tracer.app.dto.PlanDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PlanValueMapper {
    PlanValueMapper INSTANCE = Mappers.getMapper(PlanValueMapper.class);

    Plan asPlan(PlanDTO planDTO);

    PlanDTO asPlanDTO(Plan Plan);

    default PlanDTO asPlanDTOCustom(Plan Plan){
        PlanDTO planDTO = asPlanDTO(Plan);
        planDTO
            .setFeatureDTOS(
                Plan.getFeatures()
                .stream().map(FeatureValueMapper.INSTANCE::asFeatureDTO)
                .collect(Collectors.toSet()));

        return planDTO;
    }

    default Plan asPlanCustom(PlanDTO planDTO){
        Plan Plan = asPlan(planDTO);
        Plan
            .setFeatures(
                planDTO.getFeatureDTOS()
                .stream().map(FeatureValueMapper.INSTANCE::asFeature)
                .collect(Collectors.toSet()));

        return Plan;
    }
}
