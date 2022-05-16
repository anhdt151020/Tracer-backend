package com.tracer.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A DeviceStatus.
 */
@Entity
@Table(name = "device_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeviceStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "battery")
    private Long battery;

    @Column(name = "memory")
    private Long memory;

    @Column(name = "internal_storage")
    private Long internalStorage;

    @Column(name = "external_storage")
    private Long externalStorage;

    @Column(name = "is_wifi")
    private Boolean isWifi;

    @Column(name = "is_sim_internet")
    private Boolean isSimInternet;

    @Column(name = "is_gps")
    private Boolean isGPS;

    @Column(name = "created")
    private Long created;

    @Column(name = "modified")
    private Long modified;

    @JsonIgnoreProperties(value = { "deviceStatus" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private DeviceInformation deviceInformation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DeviceStatus id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBattery() {
        return this.battery;
    }

    public DeviceStatus battery(Long battery) {
        this.setBattery(battery);
        return this;
    }

    public void setBattery(Long battery) {
        this.battery = battery;
    }

    public Long getMemory() {
        return this.memory;
    }

    public DeviceStatus memory(Long memory) {
        this.setMemory(memory);
        return this;
    }

    public void setMemory(Long memory) {
        this.memory = memory;
    }

    public Long getInternalStorage() {
        return this.internalStorage;
    }

    public DeviceStatus internalStorage(Long internalStorage) {
        this.setInternalStorage(internalStorage);
        return this;
    }

    public void setInternalStorage(Long internalStorage) {
        this.internalStorage = internalStorage;
    }

    public Long getExternalStorage() {
        return this.externalStorage;
    }

    public DeviceStatus externalStorage(Long externalStorage) {
        this.setExternalStorage(externalStorage);
        return this;
    }

    public void setExternalStorage(Long externalStorage) {
        this.externalStorage = externalStorage;
    }

    public Boolean getIsWifi() {
        return this.isWifi;
    }

    public DeviceStatus isWifi(Boolean isWifi) {
        this.setIsWifi(isWifi);
        return this;
    }

    public void setIsWifi(Boolean isWifi) {
        this.isWifi = isWifi;
    }

    public Boolean getIsSimInternet() {
        return this.isSimInternet;
    }

    public DeviceStatus isSimInternet(Boolean isSimInternet) {
        this.setIsSimInternet(isSimInternet);
        return this;
    }

    public void setIsSimInternet(Boolean isSimInternet) {
        this.isSimInternet = isSimInternet;
    }

    public Boolean getIsGPS() {
        return this.isGPS;
    }

    public DeviceStatus isGPS(Boolean isGPS) {
        this.setIsGPS(isGPS);
        return this;
    }

    public void setIsGPS(Boolean isGPS) {
        this.isGPS = isGPS;
    }

    public Long getCreated() {
        return this.created;
    }

    public DeviceStatus created(Long created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getModified() {
        return this.modified;
    }

    public DeviceStatus modified(Long modified) {
        this.setModified(modified);
        return this;
    }

    public void setModified(Long modified) {
        this.modified = modified;
    }

    public DeviceInformation getDeviceInformation() {
        return this.deviceInformation;
    }

    public void setDeviceInformation(DeviceInformation deviceInformation) {
        if (this.deviceInformation != null) {
            this.deviceInformation.setDeviceStatus(null);
        }
        if (deviceInformation != null) {
            deviceInformation.setDeviceStatus(this);
        }
        this.deviceInformation = deviceInformation;
    }

    public DeviceStatus deviceInformation(DeviceInformation deviceInformation) {
        this.setDeviceInformation(deviceInformation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceStatus)) {
            return false;
        }
        return id != null && id.equals(((DeviceStatus) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceStatus{" +
            "id=" + getId() +
            ", battery=" + getBattery() +
            ", memory=" + getMemory() +
            ", internalStorage=" + getInternalStorage() +
            ", externalStorage=" + getExternalStorage() +
            ", isWifi='" + getIsWifi() + "'" +
            ", isSimInternet='" + getIsSimInternet() + "'" +
            ", isGPS='" + getIsGPS() + "'" +
            ", created=" + getCreated() +
            ", modified=" + getModified() +
            "}";
    }
}
