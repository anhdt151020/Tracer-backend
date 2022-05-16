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
 * A MessengerFacebook.
 */
@Entity
@Table(name = "messenger_facebook")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MessengerFacebook implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "own_name", length = 100)
    private String ownName;

    @Size(max = 100)
    @Column(name = "receiver_name", length = 100)
    private String receiverName;

    @Size(max = 5000)
    @Column(name = "data", length = 5000)
    private String data;

    @Column(name = "date")
    private Long date;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeState type;

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

    public MessengerFacebook id(Long id) {
        this.id = id;
        return this;
    }

    public String getOwnName() {
        return this.ownName;
    }

    public MessengerFacebook ownName(String ownName) {
        this.ownName = ownName;
        return this;
    }

    public void setOwnName(String ownName) {
        this.ownName = ownName;
    }

    public String getReceiverName() {
        return this.receiverName;
    }

    public MessengerFacebook receiverName(String receiverName) {
        this.receiverName = receiverName;
        return this;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getData() {
        return this.data;
    }

    public MessengerFacebook data(String data) {
        this.data = data;
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getDate() {
        return this.date;
    }

    public MessengerFacebook date(Long date) {
        this.date = date;
        return this;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public TypeState getType() {
        return this.type;
    }

    public MessengerFacebook type(TypeState type) {
        this.type = type;
        return this;
    }

    public void setType(TypeState type) {
        this.type = type;
    }

    public StatusType getStatus() {
        return this.status;
    }

    public MessengerFacebook status(StatusType status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public DeviceInformation getDevice() {
        return this.device;
    }

    public MessengerFacebook device(DeviceInformation deviceInformation) {
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
        if (!(o instanceof MessengerFacebook)) {
            return false;
        }
        return id != null && id.equals(((MessengerFacebook) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessengerFacebook{" +
            "id=" + getId() +
            ", ownName='" + getOwnName() + "'" +
            ", receiverName='" + getReceiverName() + "'" +
            ", data='" + getData() + "'" +
            ", date=" + getDate() +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
