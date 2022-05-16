package com.tracer.app.repository;

import com.tracer.app.domain.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the Location entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationRepository extends JpaRepository<Location, Long>, JpaSpecificationExecutor<Location> {
    Page<Location> findAllByTimeAndDeviceId(Long time, Long deviceId, Pageable pageable);

    List<Location> findAllByDeviceId(Long id);

    Page<Location> findByDeviceId(Long id, Pageable pageable);
}
