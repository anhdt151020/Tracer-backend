package com.tracer.app.dto;

import com.tracer.app.domain.enumeration.SocialNetworkEnum;
import com.tracer.app.domain.enumeration.StatusType;
import com.tracer.app.domain.enumeration.TypeState;

import javax.validation.constraints.NotNull;

public class SocialNetworkDTO {
    private Long id;
    private String name;
    private String contact;
    private String data;
    private Long date;
    private TypeState type;
    private StatusType status;
    @NotNull
    private SocialNetworkEnum socialNetworkEnum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public TypeState getType() {
        return type;
    }

    public void setType(TypeState type) {
        this.type = type;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public SocialNetworkEnum getSocialNetworkEnum() {
        return socialNetworkEnum;
    }

    public void setSocialNetworkEnum(SocialNetworkEnum socialNetworkEnum) {
        this.socialNetworkEnum = socialNetworkEnum;
    }
}
