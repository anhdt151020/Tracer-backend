package com.tracer.app.dto;

import com.tracer.app.domain.enumeration.StatusType;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class PlanDTO {
    private Long id;
    @NotNull
    private String planName;
    @NotNull
    private Set<FeatureDTO> featureDTOS;
    private StatusType planStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Set<FeatureDTO> getFeatureDTOS() {
        return featureDTOS;
    }

    public void setFeatureDTOS(Set<FeatureDTO> featureDTOS) {
        this.featureDTOS = featureDTOS;
    }

    public StatusType getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(StatusType planStatus) {
        this.planStatus = planStatus;
    }
}
