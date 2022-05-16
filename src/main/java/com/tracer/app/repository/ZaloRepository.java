package com.tracer.app.repository;

import com.tracer.app.domain.Zalo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ZaloRepository extends JpaRepository<Zalo, Long> {
    Page<Zalo> findZaloByDeviceId(Long deviceId, Pageable pageable);

    Optional<Zalo> findZaloByContactAndDate(String contact, Long date);
}
