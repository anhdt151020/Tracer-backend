package com.tracer.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tracer.app.domain.DeviceInformation;
import com.tracer.app.domain.enumeration.StatusType;
import com.tracer.app.domain.enumeration.TypeState;

public class SmsPhoneDTO {
    private Long id;
    private Long smsId;
    private Long date;
    private TypeState type;
    private String name;
    private String number;
    private String data;
    private StatusType status;
    @JsonIgnore
    private DeviceInformation device;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSmsId() {
        return smsId;
    }

    public void setSmsId(Long smsId) {
        this.smsId = smsId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public DeviceInformation getDevice() {
        return device;
    }

    public void setDevice(DeviceInformation device) {
        this.device = device;
    }
}
