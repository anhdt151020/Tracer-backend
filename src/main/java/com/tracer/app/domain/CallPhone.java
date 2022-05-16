package com.tracer.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tracer.app.domain.enumeration.StatusType;
import com.tracer.app.domain.enumeration.TypeState;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CallPhone.
 */
@Entity
@Table(name = "call_phone")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CallPhone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "call_id")
    private Long callId;

    @Column(name = "date")
    private Long date;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeState type;

    @Size(max = 1000)
    @Column(name = "name", length = 1000)
    private String name;

    @Size(max = 20)
    @Column(name = "number", length = 20)
    private String number;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "record")
    private String record;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusType status;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "deviceStatus", "user", "smsPhones", "callPhones", "locations", "messengerFacebooks" },
        allowSetters = true
    )
    private DeviceInformation device;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CallPhone id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCallId() {
        return this.callId;
    }

    public CallPhone callId(Long callId) {
        this.setCallId(callId);
        return this;
    }

    public void setCallId(Long callId) {
        this.callId = callId;
    }

    public Long getDate() {
        return this.date;
    }

    public CallPhone date(Long date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public TypeState getType() {
        return this.type;
    }

    public CallPhone type(TypeState type) {
        this.setType(type);
        return this;
    }

    public void setType(TypeState type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public CallPhone name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return this.number;
    }

    public CallPhone number(String number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getDuration() {
        return this.duration;
    }

    public CallPhone duration(Long duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getRecord() {
        return this.record;
    }

    public CallPhone record(String record) {
        this.setRecord(record);
        return this;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public StatusType getStatus() {
        return this.status;
    }

    public CallPhone status(StatusType status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public DeviceInformation getDevice() {
        return this.device;
    }

    public void setDevice(DeviceInformation deviceInformation) {
        this.device = deviceInformation;
    }

    public CallPhone device(DeviceInformation deviceInformation) {
        this.setDevice(deviceInformation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CallPhone)) {
            return false;
        }

        CallPhone other = (CallPhone) o;
        return id != null && id.equals(((CallPhone) o).id) || (Objects.equals(other.getDate(), this.getDate()) && other.getNumber().equals(this.getNumber()));
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CallPhone{" +
            "id=" + getId() +
            ", callId=" + getCallId() +
            ", date=" + getDate() +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", number='" + getNumber() + "'" +
            ", duration=" + getDuration() +
            ", record='" + getRecord() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
