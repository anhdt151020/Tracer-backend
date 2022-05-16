package com.tracer.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tracer.app.domain.DeviceInformation;

public class ContactDTO {
    private Long id;
    private String contactId;
    private String displayName;
    private String phoneNumber;
    @JsonIgnore
    private DeviceInformation device;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public DeviceInformation getDevice() {
        return device;
    }

    public void setDevice(DeviceInformation device) {
        this.device = device;
    }
}
