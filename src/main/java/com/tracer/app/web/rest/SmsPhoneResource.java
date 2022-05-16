package com.tracer.app.web.rest;

import com.tracer.app.domain.SmsPhone;
import com.tracer.app.dto.*;
import com.tracer.app.repository.SmsPhoneRepository;
import com.tracer.app.service.SmsPhoneService;
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
 * REST controller for managing {@link SmsPhone}.
 */
@RestController
@RequestMapping("/api")
public class SmsPhoneResource {

    private final Logger log = LoggerFactory.getLogger(SmsPhoneResource.class);

    private static final String ENTITY_NAME = "smsPhone";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SmsPhoneService smsPhoneService;

    private final SmsPhoneRepository smsPhoneRepository;

    public SmsPhoneResource(SmsPhoneService smsPhoneService, SmsPhoneRepository smsPhoneRepository) {
        this.smsPhoneService = smsPhoneService;
        this.smsPhoneRepository = smsPhoneRepository;
    }

    /**
     * {@code POST  /sms-phones} : Create a new smsPhone.
     *
     * @param deviceId,smsPhoneDTOS the smsPhone to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new smsPhone, or with status {@code 400 (Bad Request)} if the smsPhone has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sms-phones/list/{deviceId}")
    public void createSmsPhones(@PathVariable("deviceId") String deviceId,@Valid @RequestBody List<SmsPhoneDTO> smsPhoneDTOS) throws URISyntaxException {
        log.debug("REST request to save SmsPhones : {}",deviceId, smsPhoneDTOS);

        smsPhoneService.saveSmsPhones(deviceId,smsPhoneDTOS);

        log.info("Success");
    }

    @PostMapping("/sms-phones/{deviceId}")
    public ResponseEntity<SmsPhoneDTO> createCallPhone(@PathVariable("deviceId") String deviceId, @Valid @RequestBody SmsPhoneDTO smsPhoneDTO) throws URISyntaxException {
        SmsPhoneDTO result = smsPhoneService.saveSmsPhone(deviceId,smsPhoneDTO);

        return ResponseEntity
            .created(new URI("/api/sms-phones/" + ((result != null) ? result.getId() : "" )))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ((result != null) ? result.getId() : "" ).toString()))
            .body(result);
    }


    /**
     * {@code PUT  /sms-phones/:id} : Updates an existing smsPhone.
     *
     * @param id the id of the smsPhone to save.
     * @param smsPhone the smsPhone to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated smsPhone,
     * or with status {@code 400 (Bad Request)} if the smsPhone is not valid,
     * or with status {@code 500 (Internal Server Error)} if the smsPhone couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sms-phones/{id}")
    public ResponseEntity<SmsPhone> updateSmsPhone(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SmsPhone smsPhone
    ) throws URISyntaxException {
        log.debug("REST request to update SmsPhone : {}, {}", id, smsPhone);
        if (smsPhone.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, smsPhone.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!smsPhoneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SmsPhone result = smsPhoneService.save(smsPhone);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, smsPhone.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sms-phones/:id} : Partial updates given fields of an existing smsPhone, field will ignore if it is null
     *
     * @param id the id of the smsPhone to save.
     * @param smsPhone the smsPhone to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated smsPhone,
     * or with status {@code 400 (Bad Request)} if the smsPhone is not valid,
     * or with status {@code 404 (Not Found)} if the smsPhone is not found,
     * or with status {@code 500 (Internal Server Error)} if the smsPhone couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sms-phones/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SmsPhone> partialUpdateSmsPhone(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SmsPhone smsPhone
    ) throws URISyntaxException {
        log.debug("REST request to partial update SmsPhone partially : {}, {}", id, smsPhone);
        if (smsPhone.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, smsPhone.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!smsPhoneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SmsPhone> result = smsPhoneService.partialUpdate(smsPhone);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, smsPhone.getId().toString())
        );
    }

    @GetMapping("/user/sms-phones/{deviceId}")
    public ResponseEntity<List<SmsPhoneDTO>> getAllSmsPhoneByUserLoginAndDeviceId(
        @PathVariable String deviceId, Pageable pageable
    ) {
        Page<SmsPhoneDTO> page = smsPhoneService.findByUserLoginAndDeviceId(deviceId,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/user/sms-phones/{deviceId}/{number}")
    public ResponseEntity<List<SmsPhoneDTO>> getAllSmsPhoneByDeviceIdAndNumber(
        @PathVariable String deviceId, @PathVariable String number, Pageable pageable
    ) {
        Page<SmsPhoneDTO> page = smsPhoneService.findByUserLoginAndDeviceIdWithNumberPhone(deviceId,number,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/user/sms-phones/sms-contacts/{deviceId}")
    public ResponseEntity<List<NameAndNumberDTO>> getAllSmsPhoneContactByUserLoginAndDeviceId(
        @PathVariable String deviceId, Pageable pageable
    ) {
        Page<NameAndNumberDTO> page = smsPhoneService.findAllSmsPhoneContactByUserLoginAndDeviceId(deviceId,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/admin/sms-phones/{login}/{deviceId}")
    public ResponseEntity<List<SmsPhoneDTO>> getAllSmsPhoneByUserLoginAndDeviceIdForAdmin(
        @PathVariable String login, @PathVariable String deviceId, Pageable pageable
    ) {
        Page<SmsPhoneDTO> page = smsPhoneService.findByUserLoginAndDeviceIdForAdmin(login,deviceId,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/admin/sms-phones/{login}/{deviceId}/{number}")
    public ResponseEntity<List<SmsPhoneDTO>> getAllSmsPhoneByDeviceIdAndNumberForAdmin(
        @PathVariable String login ,@PathVariable String deviceId, @PathVariable String number, Pageable pageable
    ) {
        Page<SmsPhoneDTO> page = smsPhoneService.findByUserLoginAndDeviceIdWithNumberPhoneForAdmin(login, deviceId,number,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code DELETE  /sms-phones/:id} : delete the "id" smsPhone.
     *
     * @param id the id of the smsPhone to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sms-phones/{id}")
    public ResponseEntity<Void> deleteSmsPhone(@PathVariable Long id) {
        log.debug("REST request to delete SmsPhone : {}", id);
        smsPhoneService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/user/smsphones/name-phone/{deviceId}")
    public ResponseEntity<List<NameAndNumberDTO>> searchNameAndNumber(@PathVariable("deviceId") String deviceId, @Valid @RequestBody SearchDTO searchDTO, Pageable pageable) {
        searchDTO.setDeviceId(deviceId);
        Page<NameAndNumberDTO> page = smsPhoneService.findNameAndNumber(searchDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/user/smsphones/data/{deviceId}")
    public ResponseEntity<List<NameAndNumberDTO>> searchNameAndNumberForData(@PathVariable("deviceId") String deviceId, @Valid @RequestBody SearchDTO searchDTO, Pageable pageable) {
        searchDTO.setDeviceId(deviceId);
        Page<NameAndNumberDTO> page = smsPhoneService.findNameAndNumberForData(searchDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/user/sms-phones/search/{deviceId}")
    public ResponseEntity<List<SmsPhoneDTO>> searchSmsPhones(
        @PathVariable String deviceId,@Valid @RequestBody SearchDTO searchDTO, Pageable pageable
    ) {
        searchDTO.setDeviceId(deviceId);
        Page<SmsPhoneDTO> page = smsPhoneService.filterSearch(searchDTO,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
