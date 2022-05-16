package com.tracer.app.repository;

import com.tracer.app.domain.MessengerFacebook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MessengerFacebook entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessengerFacebookRepository extends JpaRepository<MessengerFacebook, Long> {}
