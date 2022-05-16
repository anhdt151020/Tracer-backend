package com.tracer.app.repository;

import com.tracer.app.domain.UserActivePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserActivePlanRepository extends JpaRepository<UserActivePlan,Long>, JpaSpecificationExecutor<UserActivePlan> {
    Optional<UserActivePlan> findByIdAndUserIdAndPlanId(Long id, Long userId, Long planId);

}
