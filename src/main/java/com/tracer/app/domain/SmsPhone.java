package com.tracer.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tracer.app.domain.enumeration.StatusType;
import com.tracer.app.domain.enumeration.TypeState;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A SmsPhone.
 */
@Entity
@Table(name = "sms_phone")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SmsPhone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sms_id")
    private Long smsId;

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

    @Size(max = 5000)
    @Column(name = "data", length = 5000)
    private String data;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusType status;

    @ManyToOne
    @JsonIgnoreProperties(value = { "deviceStatus", "smsPhones", "callPhones", "locations", "messengerFacebooks" }, allowSetters = true)
    private DeviceInformation device;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SmsPhone id(Long id) {
        this.id = id;
        return this;
    }

    public Long getSmsId() {
        return this.smsId;
    }

    public SmsPhone smsId(Long smsId) {
        this.smsId = smsId;
        return this;
    }

    public void setSmsId(Long smsId) {
        this.smsId = smsId;
    }

    public Long getDate() {
        return this.date;
    }

    public SmsPhone date(Long date) {
        this.date = date;
        return this;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public TypeState getType() {
        return this.type;
    }

    public SmsPhone type(TypeState type) {
        this.type = type;
        return this;
    }

    public void setType(TypeState type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public SmsPhone name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return this.number;
    }

    public SmsPhone number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getData() {
        return this.data;
    }

    public SmsPhone data(String data) {
        this.data = data;
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public StatusType getStatus() {
        return this.status;
    }

    public SmsPhone status(StatusType status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public DeviceInformation getDevice() {
        return this.device;
    }

    public SmsPhone device(DeviceInformation deviceInformation) {
        this.setDevice(deviceInformation);
        return this;
    }

    public void setDevice(DeviceInformation deviceInformation) {
        this.device = deviceInformation;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SmsPhone)) {
            return false;
        }
        return (id != null && id.equals(((SmsPhone) o).id)) || ((SmsPhone) o).smsId.equals(smsId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SmsPhone{" +
            "id=" + getId() +
            ", smsId=" + getSmsId() +
            ", date=" + getDate() +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", number='" + getNumber() + "'" +
            ", data='" + getData() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
