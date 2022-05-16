package com.tracer.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tracer.app.domain.DeviceInformation;

public class DeviceStatusDTO {
    private Long id;
    private Long battery;
    private Long memory;
    private Long internalStorage;
    private Long externalStorage;
    private Boolean isWifi;
    private Boolean isSimInternet;
    private Boolean isGPS;
    private Long created;
    private Long modified;
    @JsonIgnore
    private DeviceInformation deviceInformation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBattery() {
        return battery;
    }

    public void setBattery(Long battery) {
        this.battery = battery;
    }

    public Long getMemory() {
        return memory;
    }

    public void setMemory(Long memory) {
        this.memory = memory;
    }

    public Long getInternalStorage() {
        return internalStorage;
    }

    public void setInternalStorage(Long internalStorage) {
        this.internalStorage = internalStorage;
    }

    public Long getExternalStorage() {
        return externalStorage;
    }

    public void setExternalStorage(Long externalStorage) {
        this.externalStorage = externalStorage;
    }

    public Boolean getWifi() {
        return isWifi;
    }

    public void setWifi(Boolean wifi) {
        isWifi = wifi;
    }

    public Boolean getSimInternet() {
        return isSimInternet;
    }

    public void setSimInternet(Boolean simInternet) {
        isSimInternet = simInternet;
    }

    public Boolean getGPS() {
        return isGPS;
    }

    public void setGPS(Boolean GPS) {
        isGPS = GPS;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getModified() {
        return modified;
    }

    public void setModified(Long modified) {
        this.modified = modified;
    }

    public DeviceInformation getDeviceInformation() {
        return deviceInformation;
    }

    public void setDeviceInformation(DeviceInformation deviceInformation) {
        this.deviceInformation = deviceInformation;
    }
}
