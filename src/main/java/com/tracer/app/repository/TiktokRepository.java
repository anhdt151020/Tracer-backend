package com.tracer.app.repository;

import com.tracer.app.domain.Instagram;
import com.tracer.app.domain.Tiktok;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TiktokRepository extends JpaRepository<Tiktok, Long> {
    Page<Tiktok> findTiktokByDeviceId(Long deviceId, Pageable pageable);

    Optional<Tiktok> findTiktokByContactAndDate(String contact, Long date);
}
