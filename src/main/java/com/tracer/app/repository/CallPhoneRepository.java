package com.tracer.app.repository;

import com.tracer.app.domain.CallPhone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the CallPhone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CallPhoneRepository extends JpaRepository<CallPhone, Long> , JpaSpecificationExecutor<CallPhone> {
    List<CallPhone> findAllByDeviceId(Long deviceId);

    Optional<CallPhone> findByDeviceIdAndDateEqualsAndNumberEquals(Long deviceId, Long date, String number);

    Page<CallPhone> findByDeviceId(Long id, Pageable pageable);
}
