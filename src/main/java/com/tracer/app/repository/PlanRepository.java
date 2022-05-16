package com.tracer.app.repository;

import com.tracer.app.domain.Plan;
import com.tracer.app.domain.enumeration.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan,Long>, JpaSpecificationExecutor<Plan> {
    Optional<Plan> findByPlanNameAndPlanStatus(String planName, StatusType status);

    Optional<Plan> findByPlanName(String planName);

    Optional<Plan> findByIdIsNotAndPlanName(Long id, String planName);
}
