package com.tracer.app.web.rest;

import com.tracer.app.domain.UserPayment;
import com.tracer.app.repository.UserPaymentRepository;
import com.tracer.app.service.UserPaymentService;
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
 * REST controller for managing {@link UserPayment}.
 */
@RestController
@RequestMapping("/api")
public class UserPaymentResource {

    private final Logger log = LoggerFactory.getLogger(UserPaymentResource.class);

    private static final String ENTITY_NAME = "userPayment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserPaymentService userPaymentService;

    private final UserPaymentRepository userPaymentRepository;

    public UserPaymentResource(UserPaymentService userPaymentService, UserPaymentRepository userPaymentRepository) {
        this.userPaymentService = userPaymentService;
        this.userPaymentRepository = userPaymentRepository;
    }

    /**
     * {@code POST  /user-payments} : Create a new userPayment.
     *
     * @param userPayment the userPayment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userPayment, or with status {@code 400 (Bad Request)} if the userPayment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-payments")
    public ResponseEntity<UserPayment> createUserPayment(@Valid @RequestBody UserPayment userPayment) throws URISyntaxException {
        log.debug("REST request to save UserPayment : {}", userPayment);
        if (userPayment.getId() != null) {
            throw new BadRequestAlertException("A new userPayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserPayment result = userPaymentService.save(userPayment);
        return ResponseEntity
            .created(new URI("/api/user-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-payments/:id} : Updates an existing userPayment.
     *
     * @param id the id of the userPayment to save.
     * @param userPayment the userPayment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userPayment,
     * or with status {@code 400 (Bad Request)} if the userPayment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userPayment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-payments/{id}")
    public ResponseEntity<UserPayment> updateUserPayment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserPayment userPayment
    ) throws URISyntaxException {
        log.debug("REST request to update UserPayment : {}, {}", id, userPayment);
        if (userPayment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userPayment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userPaymentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserPayment result = userPaymentService.save(userPayment);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userPayment.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-payments/:id} : Partial updates given fields of an existing userPayment, field will ignore if it is null
     *
     * @param id the id of the userPayment to save.
     * @param userPayment the userPayment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userPayment,
     * or with status {@code 400 (Bad Request)} if the userPayment is not valid,
     * or with status {@code 404 (Not Found)} if the userPayment is not found,
     * or with status {@code 500 (Internal Server Error)} if the userPayment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-payments/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<UserPayment> partialUpdateUserPayment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserPayment userPayment
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserPayment partially : {}, {}", id, userPayment);
        if (userPayment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userPayment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userPaymentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserPayment> result = userPaymentService.partialUpdate(userPayment);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userPayment.getId().toString())
        );
    }

    /**
     * {@code GET  /user-payments} : get all the userPayments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userPayments in body.
     */
    @GetMapping("/user-payments")
    public ResponseEntity<List<UserPayment>> getAllUserPayments(Pageable pageable) {
        log.debug("REST request to get a page of UserPayments");
        Page<UserPayment> page = userPaymentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-payments/:id} : get the "id" userPayment.
     *
     * @param id the id of the userPayment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userPayment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-payments/{id}")
    public ResponseEntity<UserPayment> getUserPayment(@PathVariable Long id) {
        log.debug("REST request to get UserPayment : {}", id);
        Optional<UserPayment> userPayment = userPaymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userPayment);
    }

    /**
     * {@code DELETE  /user-payments/:id} : delete the "id" userPayment.
     *
     * @param id the id of the userPayment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-payments/{id}")
    public ResponseEntity<Void> deleteUserPayment(@PathVariable Long id) {
        log.debug("REST request to delete UserPayment : {}", id);
        userPaymentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
