package com.tracer.app.service;

import com.tracer.app.domain.UserPayment;
import com.tracer.app.repository.UserPaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserPayment}.
 */
@Service
@Transactional
public class UserPaymentService {

    private final Logger log = LoggerFactory.getLogger(UserPaymentService.class);

    private final UserPaymentRepository userPaymentRepository;

    public UserPaymentService(UserPaymentRepository userPaymentRepository) {
        this.userPaymentRepository = userPaymentRepository;
    }

    /**
     * Save a userPayment.
     *
     * @param userPayment the entity to save.
     * @return the persisted entity.
     */
    public UserPayment save(UserPayment userPayment) {
        log.debug("Request to save UserPayment : {}", userPayment);
        return userPaymentRepository.save(userPayment);
    }

    /**
     * Partially update a userPayment.
     *
     * @param userPayment the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserPayment> partialUpdate(UserPayment userPayment) {
        log.debug("Request to partially update UserPayment : {}", userPayment);

        return userPaymentRepository
            .findById(userPayment.getId())
            .map(
                existingUserPayment -> {
                    if (userPayment.getBalance() != null) {
                        existingUserPayment.setBalance(userPayment.getBalance());
                    }
                    if (userPayment.getCreated() != null) {
                        existingUserPayment.setCreated(userPayment.getCreated());
                    }
                    if (userPayment.getCreatedBy() != null) {
                        existingUserPayment.setCreatedBy(userPayment.getCreatedBy());
                    }
                    if (userPayment.getModified() != null) {
                        existingUserPayment.setModified(userPayment.getModified());
                    }
                    if (userPayment.getModifiedBy() != null) {
                        existingUserPayment.setModifiedBy(userPayment.getModifiedBy());
                    }

                    return existingUserPayment;
                }
            )
            .map(userPaymentRepository::save);
    }

    /**
     * Get all the userPayments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserPayment> findAll(Pageable pageable) {
        log.debug("Request to get all UserPayments");
        return userPaymentRepository.findAll(pageable);
    }

    /**
     * Get one userPayment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserPayment> findOne(Long id) {
        log.debug("Request to get UserPayment : {}", id);
        return userPaymentRepository.findById(id);
    }

    /**
     * Delete the userPayment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserPayment : {}", id);
        userPaymentRepository.deleteById(id);
    }
}
