package com.tracer.app.service;

import com.tracer.app.domain.MessengerFacebook;
import com.tracer.app.repository.MessengerFacebookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MessengerFacebook}.
 */
@Service
@Transactional
public class MessengerFacebookService {

    private final Logger log = LoggerFactory.getLogger(MessengerFacebookService.class);

    private final MessengerFacebookRepository messengerFacebookRepository;

    public MessengerFacebookService(MessengerFacebookRepository messengerFacebookRepository) {
        this.messengerFacebookRepository = messengerFacebookRepository;
    }

    /**
     * Save a messengerFacebook.
     *
     * @param messengerFacebook the entity to save.
     * @return the persisted entity.
     */
    public MessengerFacebook save(MessengerFacebook messengerFacebook) {
        log.debug("Request to save MessengerFacebook : {}", messengerFacebook);
        return messengerFacebookRepository.save(messengerFacebook);
    }

    /**
     * Partially update a messengerFacebook.
     *
     * @param messengerFacebook the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MessengerFacebook> partialUpdate(MessengerFacebook messengerFacebook) {
        log.debug("Request to partially update MessengerFacebook : {}", messengerFacebook);

        return messengerFacebookRepository
            .findById(messengerFacebook.getId())
            .map(
                existingMessengerFacebook -> {
                    if (messengerFacebook.getOwnName() != null) {
                        existingMessengerFacebook.setOwnName(messengerFacebook.getOwnName());
                    }
                    if (messengerFacebook.getReceiverName() != null) {
                        existingMessengerFacebook.setReceiverName(messengerFacebook.getReceiverName());
                    }
                    if (messengerFacebook.getData() != null) {
                        existingMessengerFacebook.setData(messengerFacebook.getData());
                    }
                    if (messengerFacebook.getDate() != null) {
                        existingMessengerFacebook.setDate(messengerFacebook.getDate());
                    }
                    if (messengerFacebook.getType() != null) {
                        existingMessengerFacebook.setType(messengerFacebook.getType());
                    }
                    if (messengerFacebook.getStatus() != null) {
                        existingMessengerFacebook.setStatus(messengerFacebook.getStatus());
                    }

                    return existingMessengerFacebook;
                }
            )
            .map(messengerFacebookRepository::save);
    }

    /**
     * Get all the messengerFacebooks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MessengerFacebook> findAll(Pageable pageable) {
        log.debug("Request to get all MessengerFacebooks");
        return messengerFacebookRepository.findAll(pageable);
    }

    /**
     * Get one messengerFacebook by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MessengerFacebook> findOne(Long id) {
        log.debug("Request to get MessengerFacebook : {}", id);
        return messengerFacebookRepository.findById(id);
    }

    /**
     * Delete the messengerFacebook by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MessengerFacebook : {}", id);
        messengerFacebookRepository.deleteById(id);
    }
}
