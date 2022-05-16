package com.tracer.app.domain;

import com.tracer.app.domain.enumeration.TransactionType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A TransactionHistory.
 */
@Entity
@Table(name = "transaction_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TransactionHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_state")
    private TransactionType transactionState;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "total_value")
    private Float totalValue;

    @Size(max = 1000)
    @Column(name = "error_msq", length = 1000)
    private String errorMsq;

    @Column(name = "created")
    private Long created;

    @Size(max = 1000)
    @Column(name = "created_by", length = 1000)
    private String createdBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionHistory id(Long id) {
        this.id = id;
        return this;
    }

    public TransactionType getTransactionState() {
        return this.transactionState;
    }

    public TransactionHistory transactionState(TransactionType transactionState) {
        this.transactionState = transactionState;
        return this;
    }

    public void setTransactionState(TransactionType transactionState) {
        this.transactionState = transactionState;
    }

    public Long getUserId() {
        return this.userId;
    }

    public TransactionHistory userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Float getTotalValue() {
        return this.totalValue;
    }

    public TransactionHistory totalValue(Float totalValue) {
        this.totalValue = totalValue;
        return this;
    }

    public void setTotalValue(Float totalValue) {
        this.totalValue = totalValue;
    }

    public String getErrorMsq() {
        return this.errorMsq;
    }

    public TransactionHistory errorMsq(String errorMsq) {
        this.errorMsq = errorMsq;
        return this;
    }

    public void setErrorMsq(String errorMsq) {
        this.errorMsq = errorMsq;
    }

    public Long getCreated() {
        return this.created;
    }

    public TransactionHistory created(Long created) {
        this.created = created;
        return this;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public TransactionHistory createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionHistory)) {
            return false;
        }
        return id != null && id.equals(((TransactionHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionHistory{" +
            "id=" + getId() +
            ", transactionState='" + getTransactionState() + "'" +
            ", userId=" + getUserId() +
            ", totalValue=" + getTotalValue() +
            ", errorMsq='" + getErrorMsq() + "'" +
            ", created=" + getCreated() +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
