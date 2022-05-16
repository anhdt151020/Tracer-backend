package com.tracer.app.repository;

import com.tracer.app.domain.Skype;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkypeRepository extends JpaRepository<Skype, Long> {
    Page<Skype> findSkypeByDeviceId(Long deviceId, Pageable pageable);

    Optional<Skype> findSkypeByContactAndDate(String contact, Long date);
}
