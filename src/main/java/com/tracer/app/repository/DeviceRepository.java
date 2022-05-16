package com.tracer.app.repository;

import com.tracer.app.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the Device entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> , JpaSpecificationExecutor<Device> {

    Optional<Device> findByUserIdAndDeviceId(Long userId, String deviceId);

    List<Device> findByUserId(Long userId);
}
