package com.tracer.app.domain;

import com.tracer.app.domain.enumeration.StatusType;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "plan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Plan implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plan_name")
    private String planName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "plan_feature",
        joinColumns = @JoinColumn(name = "plan_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "feature_id", referencedColumnName = "id"))
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Feature> features = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "plan_status")
    private StatusType planStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Set<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(Set<Feature> features) {
        this.features = features;
    }

    public StatusType getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(StatusType planStatus) {
        this.planStatus = planStatus;
    }

    @Override
    public String toString() {
        return "Plan{" +
            "id=" + id +
            ", planName='" + planName + '\'' +
            ", features=" + features +
            ", planStatus='" + planStatus + '\'' +
            '}';
    }
    public void enrollFeature(Feature feature) {
        features.add(feature);
    }

}
