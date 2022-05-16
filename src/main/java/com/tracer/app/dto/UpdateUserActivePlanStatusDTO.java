package com.tracer.app.dto;

import com.tracer.app.domain.enumeration.StatusType;

import javax.validation.constraints.NotNull;

public class UpdateUserActivePlanStatusDTO {
    @NotNull
    private Long planId;
    @NotNull
    private Long userId;
    @NotNull
    private StatusType statusType;

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }
}
