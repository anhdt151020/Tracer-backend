package com.tracer.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tracer.app.domain.enumeration.StatusType;
import com.tracer.app.domain.enumeration.TypeState;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "facebook_messenger")

public class FacebookMessenger implements Serializable {
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

    public FacebookMessenger id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public FacebookMessenger name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public FacebookMessenger contact(String contact) {
        this.setContact(contact);
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getData() {
        return data;
    }

    public FacebookMessenger facebookMessenger(String data) {
        this.setData(data);
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }


    public Long getDate() {
        return date;
    }

    public FacebookMessenger date(Long date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public TypeState getType() {
        return type;
    }

    public FacebookMessenger type(TypeState type) {
        this.setType(type);
        return this;
    }

    public void setType(TypeState type) {
        this.type = type;
    }

    public StatusType getStatus() {
        return status;
    }

    public FacebookMessenger status(StatusType status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public DeviceInformation getDevice() {
        return device;
    }

    public FacebookMessenger device(DeviceInformation deviceInformation) {
        this.setDevice(deviceInformation);
        return this;
    }

    public void setDevice(DeviceInformation device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "FacebookMessenger{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", contact='" + contact + '\'' +
            ", data='" + data + '\'' +
            ", date=" + date +
            ", type=" + type +
            ", status=" + status +
            ", device=" + device +
            '}';
    }
}
