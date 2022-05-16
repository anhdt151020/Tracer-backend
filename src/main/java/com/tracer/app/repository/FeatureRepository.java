package com.tracer.app.repository;

import com.tracer.app.domain.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FeatureRepository extends JpaRepository<Feature,Long> , JpaSpecificationExecutor<Feature> {
    Set<Feature> findAllByIdIn(Set<Long> ids);

}
