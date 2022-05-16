package com.tracer.app.repository;

import com.tracer.app.domain.FacebookMessenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data SQL repository for the CallPhone entity.
 */
@Repository
public interface FacebookMessengerRepository extends JpaRepository<FacebookMessenger,Long> {
    Page<FacebookMessenger> findFacebookMessengerByDeviceId(Long deviceId, Pageable pageable);

    Optional<FacebookMessenger> findFacebookMessengerByContactAndDate(String contact, Long date);
}
