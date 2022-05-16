package com.tracer.app.dto.mapper;

import com.tracer.app.domain.UserActivePlan;
import com.tracer.app.dto.UserActivePlanDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserActivePlanValueMapper {
    UserActivePlanDTO asUserActivePlanDTO(UserActivePlan userActivePlan);

    UserActivePlan asUserActivePlan(UserActivePlanDTO userActivePlanDTO);

    default UserActivePlan asUserActivePlanCustom(UserActivePlanDTO userActivePlanDTO){
        UserActivePlan userActivePlan = asUserActivePlan(userActivePlanDTO);
        userActivePlan.setPlan(PlanValueMapper.INSTANCE.asPlanCustom(userActivePlanDTO.getPlanDTO()));

        return userActivePlan;
    }

    default UserActivePlanDTO asUserActivePlanDTOCustom(UserActivePlan userActivePlan){
        UserActivePlanDTO userActivePlanDTO = asUserActivePlanDTO(userActivePlan);
        userActivePlanDTO.setPlanDTO(PlanValueMapper.INSTANCE.asPlanDTOCustom(userActivePlan.getPlan()));

        return userActivePlanDTO;
    }
}
