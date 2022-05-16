package com.tracer.app.web.rest;

import com.tracer.app.domain.MessengerFacebook;
import com.tracer.app.repository.MessengerFacebookRepository;
import com.tracer.app.service.MessengerFacebookService;
import com.tracer.app.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link MessengerFacebook}.
 */
@RestController
@RequestMapping("/api")
public class MessengerFacebookResource {

    private final Logger log = LoggerFactory.getLogger(MessengerFacebookResource.class);

    private static final String ENTITY_NAME = "messengerFacebook";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MessengerFacebookService messengerFacebookService;

    private final MessengerFacebookRepository messengerFacebookRepository;

    public MessengerFacebookResource(
        MessengerFacebookService messengerFacebookService,
        MessengerFacebookRepository messengerFacebookRepository
    ) {
        this.messengerFacebookService = messengerFacebookService;
        this.messengerFacebookRepository = messengerFacebookRepository;
    }

    /**
     * {@code POST  /messenger-facebooks} : Create a new messengerFacebook.
     *
     * @param messengerFacebook the messengerFacebook to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new messengerFacebook, or with status {@code 400 (Bad Request)} if the messengerFacebook has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/messenger-facebooks")
    public ResponseEntity<MessengerFacebook> createMessengerFacebook(@Valid @RequestBody MessengerFacebook messengerFacebook)
        throws URISyntaxException {
        log.debug("REST request to save MessengerFacebook : {}", messengerFacebook);
        if (messengerFacebook.getId() != null) {
            throw new BadRequestAlertException("A new messengerFacebook cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MessengerFacebook result = messengerFacebookService.save(messengerFacebook);
        return ResponseEntity
            .created(new URI("/api/messenger-facebooks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /messenger-facebooks/:id} : Updates an existing messengerFacebook.
     *
     * @param id the id of the messengerFacebook to save.
     * @param messengerFacebook the messengerFacebook to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messengerFacebook,
     * or with status {@code 400 (Bad Request)} if the messengerFacebook is not valid,
     * or with status {@code 500 (Internal Server Error)} if the messengerFacebook couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/messenger-facebooks/{id}")
    public ResponseEntity<MessengerFacebook> updateMessengerFacebook(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MessengerFacebook messengerFacebook
    ) throws URISyntaxException {
        log.debug("REST request to update MessengerFacebook : {}, {}", id, messengerFacebook);
        if (messengerFacebook.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, messengerFacebook.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!messengerFacebookRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MessengerFacebook result = messengerFacebookService.save(messengerFacebook);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, messengerFacebook.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /messenger-facebooks/:id} : Partial updates given fields of an existing messengerFacebook, field will ignore if it is null
     *
     * @param id the id of the messengerFacebook to save.
     * @param messengerFacebook the messengerFacebook to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messengerFacebook,
     * or with status {@code 400 (Bad Request)} if the messengerFacebook is not valid,
     * or with status {@code 404 (Not Found)} if the messengerFacebook is not found,
     * or with status {@code 500 (Internal Server Error)} if the messengerFacebook couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/messenger-facebooks/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<MessengerFacebook> partialUpdateMessengerFacebook(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MessengerFacebook messengerFacebook
    ) throws URISyntaxException {
        log.debug("REST request to partial update MessengerFacebook partially : {}, {}", id, messengerFacebook);
        if (messengerFacebook.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, messengerFacebook.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!messengerFacebookRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MessengerFacebook> result = messengerFacebookService.partialUpdate(messengerFacebook);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, messengerFacebook.getId().toString())
        );
    }

    /**
     * {@code GET  /messenger-facebooks} : get all the messengerFacebooks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of messengerFacebooks in body.
     */
    @GetMapping("/messenger-facebooks")
    public ResponseEntity<List<MessengerFacebook>> getAllMessengerFacebooks(Pageable pageable) {
        log.debug("REST request to get a page of MessengerFacebooks");
        Page<MessengerFacebook> page = messengerFacebookService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /messenger-facebooks/:id} : get the "id" messengerFacebook.
     *
     * @param id the id of the messengerFacebook to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the messengerFacebook, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/messenger-facebooks/{id}")
    public ResponseEntity<MessengerFacebook> getMessengerFacebook(@PathVariable Long id) {
        log.debug("REST request to get MessengerFacebook : {}", id);
        Optional<MessengerFacebook> messengerFacebook = messengerFacebookService.findOne(id);
        return ResponseUtil.wrapOrNotFound(messengerFacebook);
    }

    /**
     * {@code DELETE  /messenger-facebooks/:id} : delete the "id" messengerFacebook.
     *
     * @param id the id of the messengerFacebook to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/messenger-facebooks/{id}")
    public ResponseEntity<Void> deleteMessengerFacebook(@PathVariable Long id) {
        log.debug("REST request to delete MessengerFacebook : {}", id);
        messengerFacebookService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
