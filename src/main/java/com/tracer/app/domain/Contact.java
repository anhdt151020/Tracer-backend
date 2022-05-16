package com.tracer.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "contact")
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contact_id", length = 255, nullable = false)
    private String contactId;
    @Column(name = "display_name", length = 255, nullable = false)
    private String displayName;
    @Column(name = "phone_number", length = 255, nullable = false)
    private String phoneNumber;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {"deviceStatus", "user", "smsPhones", "callPhones", "locations", "messengerFacebooks"},
        allowSetters = true
    )
    private DeviceInformation device;

    public Contact(Long id, String contactId, String displayName, String phoneNumber) {
        this.id = id;
        this.contactId = contactId;
        this.displayName = displayName;
        this.phoneNumber = phoneNumber;
    }

    public Contact() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Contact{" +
            "id=" + id +
            ", contactId='" + contactId + '\'' +
            ", displayName='" + displayName + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            '}';
    }

    public DeviceInformation getDevice() {
        return device;
    }

    public void setDevice(DeviceInformation device) {
        this.device = device;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contact)) {
            return false;
        }
        Contact other = (Contact) o;

        return (id != null && id.equals(other.id)) || other.getPhoneNumber().equals(phoneNumber);
    }
}
