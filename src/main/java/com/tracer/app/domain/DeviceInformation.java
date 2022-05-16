package com.tracer.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tracer.app.domain.enumeration.StatusType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DeviceInformation.
 */
@Entity
@Table(name = "device_information")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeviceInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @Size(max = 500)
    @Column(name = "model", length = 500)
    private String model;

    @Size(max = 100)
    @Column(name = "version", length = 100)
    private String version;

    @Size(max = 500)
    @Column(name = "device_id", length = 500)
    private String deviceId;

    @Size(max = 20)
    @Column(name = "cell_number", length = 20)
    private String cellNumber;

    @Size(max = 100)
    @Column(name = "operator", length = 100)
    private String operator;

    @Column(name = "internal_storage")
    private Long internalStorage;

    @Column(name = "external_storage")
    private Long externalStorage;

    @Column(name = "memory")
    private Long memory;

    @Column(name = "created")
    private Long created;

    @Column(name = "modified")
    private Long modified;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusType status;

    @OneToOne(mappedBy = "deviceInformation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "deviceInformation" }, allowSetters = true)
    private DeviceStatus deviceStatus;

    @OneToMany(mappedBy = "device")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "device" }, allowSetters = true)
    private Set<SmsPhone> smsPhones = new HashSet<>();

    @OneToMany(mappedBy = "device")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "device" }, allowSetters = true)
    private Set<CallPhone> callPhones = new HashSet<>();

    @OneToMany(mappedBy = "device")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "device" }, allowSetters = true)
    private Set<Location> locations = new HashSet<>();

    @OneToMany(mappedBy = "device")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "device" }, allowSetters = true)
    private Set<MessengerFacebook> messengerFacebooks = new HashSet<>();

    @OneToMany(mappedBy = "device")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "device" }, allowSetters = true)
    private Set<FacebookMessenger> facebookMessengers = new HashSet<>();

    @OneToMany(mappedBy = "device")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "device" }, allowSetters = true)
    private Set<Instagram> instagrams = new HashSet<>();

    @OneToMany(mappedBy = "device")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "device" }, allowSetters = true)
    private Set<Skype> skypes = new HashSet<>();

    @OneToMany(mappedBy = "device")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "device" }, allowSetters = true)
    private Set<Tiktok> tiktoks = new HashSet<>();

    public Set<FacebookMessenger> getFacebookMessengers() {
        return facebookMessengers;
    }

    public void setFacebookMessengers(Set<FacebookMessenger> facebookMessengers) {
        this.facebookMessengers = facebookMessengers;
    }

    public Set<Instagram> getInstagrams() {
        return instagrams;
    }

    public void setInstagrams(Set<Instagram> instagrams) {
        this.instagrams = instagrams;
    }

    public Set<Skype> getSkypes() {
        return skypes;
    }

    public void setSkypes(Set<Skype> skypes) {
        this.skypes = skypes;
    }

    public Set<Tiktok> getTiktoks() {
        return tiktoks;
    }

    public void setTiktoks(Set<Tiktok> tiktoks) {
        this.tiktoks = tiktoks;
    }

    public Set<Zalo> getZalos() {
        return zalos;
    }

    public void setZalos(Set<Zalo> zalos) {
        this.zalos = zalos;
    }

    @OneToMany(mappedBy = "device")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "device" }, allowSetters = true)
    private Set<Zalo> zalos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeviceInformation id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public DeviceInformation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return this.model;
    }

    public DeviceInformation model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVersion() {
        return this.version;
    }

    public DeviceInformation version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public DeviceInformation deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCellNumber() {
        return this.cellNumber;
    }

    public DeviceInformation cellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
        return this;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getOperator() {
        return this.operator;
    }

    public DeviceInformation operator(String operator) {
        this.operator = operator;
        return this;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Long getInternalStorage() {
        return this.internalStorage;
    }

    public DeviceInformation internalStorage(Long internalStorage) {
        this.internalStorage = internalStorage;
        return this;
    }

    public void setInternalStorage(Long internalStorage) {
        this.internalStorage = internalStorage;
    }

    public Long getExternalStorage() {
        return this.externalStorage;
    }

    public DeviceInformation externalStorage(Long externalStorage) {
        this.externalStorage = externalStorage;
        return this;
    }

    public void setExternalStorage(Long externalStorage) {
        this.externalStorage = externalStorage;
    }

    public Long getMemory() {
        return this.memory;
    }

    public DeviceInformation memory(Long memory) {
        this.memory = memory;
        return this;
    }

    public void setMemory(Long memory) {
        this.memory = memory;
    }

    public Long getCreated() {
        return this.created;
    }

    public DeviceInformation created(Long created) {
        this.created = created;
        return this;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getModified() {
        return this.modified;
    }

    public DeviceInformation modified(Long modified) {
        this.modified = modified;
        return this;
    }

    public void setModified(Long modified) {
        this.modified = modified;
    }

    public StatusType getStatus() {
        return this.status;
    }

    public DeviceInformation status(StatusType status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public DeviceStatus getDeviceStatus() {
        return this.deviceStatus;
    }

    public DeviceInformation deviceStatus(DeviceStatus deviceStatus) {
        this.setDeviceStatus(deviceStatus);
        return this;
    }

    public void setDeviceStatus(DeviceStatus deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public Set<SmsPhone> getSmsPhones() {
        return this.smsPhones;
    }

    public DeviceInformation smsPhones(Set<SmsPhone> smsPhones) {
        this.setSmsPhones(smsPhones);
        return this;
    }

    public DeviceInformation addSmsPhones(SmsPhone smsPhone) {
        this.smsPhones.add(smsPhone);
        smsPhone.setDevice(this);
        return this;
    }

    public DeviceInformation removeSmsPhones(SmsPhone smsPhone) {
        this.smsPhones.remove(smsPhone);
        smsPhone.setDevice(null);
        return this;
    }

    public void setSmsPhones(Set<SmsPhone> smsPhones) {
        if (this.smsPhones != null) {
            this.smsPhones.forEach(i -> i.setDevice(null));
        }
        if (smsPhones != null) {
            smsPhones.forEach(i -> i.setDevice(this));
        }
        this.smsPhones = smsPhones;
    }

    public Set<CallPhone> getCallPhones() {
        return this.callPhones;
    }

    public DeviceInformation callPhones(Set<CallPhone> callPhones) {
        this.setCallPhones(callPhones);
        return this;
    }

    public DeviceInformation addCallPhones(CallPhone callPhone) {
        this.callPhones.add(callPhone);
        callPhone.setDevice(this);
        return this;
    }

    public DeviceInformation removeCallPhones(CallPhone callPhone) {
        this.callPhones.remove(callPhone);
        callPhone.setDevice(null);
        return this;
    }

    public void setCallPhones(Set<CallPhone> callPhones) {
        if (this.callPhones != null) {
            this.callPhones.forEach(i -> i.setDevice(null));
        }
        if (callPhones != null) {
            callPhones.forEach(i -> i.setDevice(this));
        }
        this.callPhones = callPhones;
    }

    public Set<Location> getLocations() {
        return this.locations;
    }

    public DeviceInformation locations(Set<Location> locations) {
        this.setLocations(locations);
        return this;
    }

    public DeviceInformation addLocations(Location location) {
        this.locations.add(location);
        location.setDevice(this);
        return this;
    }

    public DeviceInformation removeLocations(Location location) {
        this.locations.remove(location);
        location.setDevice(null);
        return this;
    }

    public void setLocations(Set<Location> locations) {
        if (this.locations != null) {
            this.locations.forEach(i -> i.setDevice(null));
        }
        if (locations != null) {
            locations.forEach(i -> i.setDevice(this));
        }
        this.locations = locations;
    }

    public Set<MessengerFacebook> getMessengerFacebooks() {
        return this.messengerFacebooks;
    }

    public DeviceInformation messengerFacebooks(Set<MessengerFacebook> messengerFacebooks) {
        this.setMessengerFacebooks(messengerFacebooks);
        return this;
    }

    public DeviceInformation addMessengerFacebooks(MessengerFacebook messengerFacebook) {
        this.messengerFacebooks.add(messengerFacebook);
        messengerFacebook.setDevice(this);
        return this;
    }

    public DeviceInformation removeMessengerFacebooks(MessengerFacebook messengerFacebook) {
        this.messengerFacebooks.remove(messengerFacebook);
        messengerFacebook.setDevice(null);
        return this;
    }

    public void setMessengerFacebooks(Set<MessengerFacebook> messengerFacebooks) {
        if (this.messengerFacebooks != null) {
            this.messengerFacebooks.forEach(i -> i.setDevice(null));
        }
        if (messengerFacebooks != null) {
            messengerFacebooks.forEach(i -> i.setDevice(this));
        }
        this.messengerFacebooks = messengerFacebooks;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceInformation)) {
            return false;
        }
        return id != null && id.equals(((DeviceInformation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceInformation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", model='" + getModel() + "'" +
            ", version='" + getVersion() + "'" +
            ", deviceId='" + getDeviceId() + "'" +
            ", cellNumber='" + getCellNumber() + "'" +
            ", operator='" + getOperator() + "'" +
            ", internalStorage=" + getInternalStorage() +
            ", externalStorage=" + getExternalStorage() +
            ", memory=" + getMemory() +
            ", created=" + getCreated() +
            ", modified=" + getModified() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
