package com.tracer.app.repository;

import com.tracer.app.domain.DeviceInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Spring Data SQL repository for the DeviceInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceInformationRepository extends JpaRepository<DeviceInformation, Long> , JpaSpecificationExecutor<DeviceInformation>{
    Optional<DeviceInformation> findByDeviceId(String deviceId);

    List<DeviceInformation> findAllByDeviceIdIn(List<String> listDeviceIds);
}
