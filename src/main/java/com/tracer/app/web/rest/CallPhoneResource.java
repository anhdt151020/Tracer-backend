package com.tracer.app.web.rest;

import com.tracer.app.domain.CallPhone;
import com.tracer.app.dto.CallPhoneDTO;
import com.tracer.app.dto.ContactDTO;
import com.tracer.app.dto.SearchDTO;
import com.tracer.app.repository.CallPhoneRepository;
import com.tracer.app.service.CallPhoneService;
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
 * REST controller for managing {@link CallPhone}.
 */
@RestController
@RequestMapping("/api")
public class CallPhoneResource {

    private final Logger log = LoggerFactory.getLogger(CallPhoneResource.class);

    private static final String ENTITY_NAME = "callPhone";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CallPhoneService callPhoneService;

    private final CallPhoneRepository callPhoneRepository;

    public CallPhoneResource(CallPhoneService callPhoneService, CallPhoneRepository callPhoneRepository) {
        this.callPhoneService = callPhoneService;
        this.callPhoneRepository = callPhoneRepository;
    }

    /**
     * {@code POST  /call-phones} : Create new callPhones.
     *
     * @param deviceId,callPhoneDTOS the callPhone to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new callPhone, or with status {@code 400 (Bad Request)} if the callPhone has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/call-phones/list/{deviceId}")
    public void createCallPhones(@PathVariable("deviceId") String deviceId,@Valid @RequestBody List<CallPhoneDTO> callPhoneDTOS) throws URISyntaxException {
        log.debug("REST request to save CallPhones : {}",deviceId ,callPhoneDTOS);

        callPhoneService.saveCallPhones(deviceId,callPhoneDTOS);

        log.info("Success");
    }

    @PostMapping("/call-phones/{deviceId}")
    public ResponseEntity<CallPhoneDTO> createCallPhone(@PathVariable("deviceId") String deviceId,@Valid @ModelAttribute CallPhoneDTO callPhoneDTO) throws URISyntaxException {
        CallPhoneDTO result = callPhoneService.saveCallPhone(deviceId,callPhoneDTO);

        return ResponseEntity
            .created(new URI("/api/call-phones/" + ((result != null) ? result.getId() : "" )))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ((result != null) ? result.getId() : "" ).toString()))
            .body(result);
    }

    /**
     * {@code PUT  /call-phones/:id} : Updates an existing callPhone.
     *
     * @param id the id of the callPhone to save.
     * @param callPhone the callPhone to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated callPhone,
     * or with status {@code 400 (Bad Request)} if the callPhone is not valid,
     * or with status {@code 500 (Internal Server Error)} if the callPhone couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/call-phones/{id}")
    public ResponseEntity<CallPhone> updateCallPhone(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CallPhone callPhone
    ) throws URISyntaxException {
        log.debug("REST request to update CallPhone : {}, {}", id, callPhone);
        if (callPhone.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, callPhone.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!callPhoneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CallPhone result = callPhoneService.save(callPhone);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, callPhone.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /call-phones/:id} : Partial updates given fields of an existing callPhone, field will ignore if it is null
     *
     * @param id the id of the callPhone to save.
     * @param callPhone the callPhone to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated callPhone,
     * or with status {@code 400 (Bad Request)} if the callPhone is not valid,
     * or with status {@code 404 (Not Found)} if the callPhone is not found,
     * or with status {@code 500 (Internal Server Error)} if the callPhone couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/call-phones/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CallPhone> partialUpdateCallPhone(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CallPhone callPhone
    ) throws URISyntaxException {
        log.debug("REST request to partial update CallPhone partially : {}, {}", id, callPhone);
        if (callPhone.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, callPhone.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!callPhoneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CallPhone> result = callPhoneService.partialUpdate(callPhone);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, callPhone.getId().toString())
        );
    }

    @PostMapping("/user/callphones/search/{deviceId}")
    public ResponseEntity<List<CallPhoneDTO>> searchContacts(@PathVariable("deviceId") String deviceId, @Valid @RequestBody SearchDTO searchDTO, Pageable pageable) {
        searchDTO.setDeviceId(deviceId);
        Page<CallPhoneDTO> page = callPhoneService.findAllByFilter(searchDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/user/call-logs/{deviceId}")
    public ResponseEntity<List<CallPhoneDTO>> getAllCallPhoneByUserLoginAndDeviceId(
        @PathVariable String deviceId, Pageable pageable
    ) {
        Page<CallPhoneDTO> page = callPhoneService.findByUserLoginAndDeviceId(deviceId,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/admin/call-logs/{login}/{deviceId}")
    public ResponseEntity<List<CallPhoneDTO>> getAllCallPhoneByUserLoginAndDeviceIdForAdmin(
        @PathVariable String login, @PathVariable String deviceId, Pageable pageable
    ) {
        Page<CallPhoneDTO> page = callPhoneService.findByUserLoginAndDeviceIdForAdmin(login,deviceId,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code DELETE  /call-phones/:id} : delete the "id" callPhone.
     *
     * @param id the id of the callPhone to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/call-phones/{id}")
    public ResponseEntity<Void> deleteCallPhone(@PathVariable Long id) {
        log.debug("REST request to delete CallPhone : {}", id);
        callPhoneService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
