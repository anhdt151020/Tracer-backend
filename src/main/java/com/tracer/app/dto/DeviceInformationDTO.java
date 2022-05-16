package com.tracer.app.dto;

import com.tracer.app.domain.enumeration.StatusType;
import org.wildfly.common.annotation.NotNull;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;

public class DeviceInformationDTO {
    private Long id;

    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String model;

    @Size(max = 100)
    private String version;

    @NotNull
    @Size(max = 500)
    private String deviceId;

    @Size(max = 20)
    private String cellNumber;

    @Size(max = 100)
    private String operator;

    private Long internalStorage;

    private Long externalStorage;

    private Long memory;

    private Long created;

    private Long modified;

    @Enumerated(EnumType.STRING)
    private StatusType status;

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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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

    public Long getMemory() {
        return memory;
    }

    public void setMemory(Long memory) {
        this.memory = memory;
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

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }
}
