package com.tracer.app.dto;

import com.tracer.app.domain.User;

import javax.validation.constraints.NotNull;

public class UserActivePlanDTO {
    private Long id;
    private User user;
    private PlanDTO planDTO;
    @NotNull
    private Long startDate;
    @NotNull
    private Long endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public PlanDTO getPlanDTO() {
        return planDTO;
    }

    public void setPlanDTO(PlanDTO planDTO) {
        this.planDTO = planDTO;
    }
}
