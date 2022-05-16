package com.tracer.app.repository;

import com.tracer.app.domain.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data SQL repository for the DeviceStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceStatusRepository extends JpaRepository<DeviceStatus, Long> , JpaSpecificationExecutor<DeviceStatus> {
    Optional<DeviceStatus> findByDeviceInformationId(Long deviceId);
}
