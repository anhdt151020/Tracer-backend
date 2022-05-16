package com.tracer.app.repository;

import com.tracer.app.domain.Instagram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstagramRepository extends JpaRepository<Instagram,Long> {
    Page<Instagram> findInstagramByDeviceId(Long deviceId, Pageable pageable);

    Optional<Instagram> findInstagramByContactAndDate(String contact, Long date);
}
