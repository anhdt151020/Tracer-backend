package com.tracer.app.domain;

import com.tracer.app.domain.enumeration.StatusType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Device.
 */
@Entity
@Table(name = "device")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id")
    private String deviceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusType status;

    @Column(name = "created")
    private Long created;

    @Column(name = "modified")
    private Long modified;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Device id(Long id) {
        this.id = id;
        return this;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public Device deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public StatusType getStatus() {
        return this.status;
    }

    public Device status(StatusType status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public Long getCreated() {
        return this.created;
    }

    public Device created(Long created) {
        this.created = created;
        return this;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getModified() {
        return this.modified;
    }

    public Device modified(Long modified) {
        this.modified = modified;
        return this;
    }

    public void setModified(Long modified) {
        this.modified = modified;
    }

    public User getUser() {
        return this.user;
    }

    public Device user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Device)) {
            return false;
        }
        return id != null && id.equals(((Device) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Device{" +
            "id=" + getId() +
            ", deviceId='" + getDeviceId() + "'" +
            ", status='" + getStatus() + "'" +
            ", created=" + getCreated() +
            ", modified=" + getModified() +
            "}";
    }
}
