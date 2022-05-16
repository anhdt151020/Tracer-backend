package com.tracer.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A UserPayment.
 */
@Entity
@Table(name = "user_payment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance")
    private Float balance;

    @Column(name = "created")
    private Long created;

    @Size(max = 1000)
    @Column(name = "created_by", length = 1000)
    private String createdBy;

    @Column(name = "modified")
    private Long modified;

    @Size(max = 1000)
    @Column(name = "modified_by", length = 1000)
    private String modifiedBy;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserPayment id(Long id) {
        this.id = id;
        return this;
    }

    public Float getBalance() {
        return this.balance;
    }

    public UserPayment balance(Float balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public Long getCreated() {
        return this.created;
    }

    public UserPayment created(Long created) {
        this.created = created;
        return this;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public UserPayment createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getModified() {
        return this.modified;
    }

    public UserPayment modified(Long modified) {
        this.modified = modified;
        return this;
    }

    public void setModified(Long modified) {
        this.modified = modified;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public UserPayment modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public User getUser() {
        return this.user;
    }

    public UserPayment user(User user) {
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
        if (!(o instanceof UserPayment)) {
            return false;
        }
        return id != null && id.equals(((UserPayment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserPayment{" +
            "id=" + getId() +
            ", balance=" + getBalance() +
            ", created=" + getCreated() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", modified=" + getModified() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            "}";
    }
}
