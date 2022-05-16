package com.tracer.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tracer.app.domain.enumeration.StatusType;
import com.tracer.app.domain.enumeration.TypeState;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "tiktok")
public class Tiktok implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 1000)
    @Column(name = "name", length = 1000)
    private String name;

    @Column(name = "contact")
    private String contact;

    @Column(name = "data")
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
    @JsonIgnoreProperties(
        value = {"deviceStatus", "user", "smsPhones", "callPhones", "locations", "messengerFacebooks", "facebookMessengers", "instagrams", "skypes", "tiktoks", "zalos"},
        allowSetters = true
    )

    private DeviceInformation device;

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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
