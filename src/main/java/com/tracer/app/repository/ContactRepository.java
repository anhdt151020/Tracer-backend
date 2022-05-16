package com.tracer.app.repository;

import com.tracer.app.domain.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long>, JpaSpecificationExecutor<Contact> {
    List<Contact> findAllByDeviceId(Long Id);
    Page<Contact> findByDeviceId(Long deviceId, Pageable pageable);
}
