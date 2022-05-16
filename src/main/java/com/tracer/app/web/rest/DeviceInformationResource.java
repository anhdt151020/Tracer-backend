package com.tracer.app.web.rest;

import com.tracer.app.domain.DeviceInformation;
import com.tracer.app.dto.DeviceInformationDTO;
import com.tracer.app.repository.DeviceInformationRepository;
import com.tracer.app.service.DeviceInformationService;
import com.tracer.app.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link DeviceInformation}.
 */
@RestController
@RequestMapping("/api")
public class DeviceInformationResource {

    private final Logger log = LoggerFactory.getLogger(DeviceInformationResource.class);

    private static final String ENTITY_NAME = "deviceInformation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeviceInformationService deviceInformationService;

    private final DeviceInformationRepository deviceInformationRepository;

    public DeviceInformationResource(
        DeviceInformationService deviceInformationService,
        DeviceInformationRepository deviceInformationRepository
    ) {
        this.deviceInformationService = deviceInformationService;
        this.deviceInformationRepository = deviceInformationRepository;
    }

    @PostMapping("/device-informations")
    public ResponseEntity<DeviceInformationDTO> createDeviceInformation(@Valid @RequestBody DeviceInformationDTO deviceInformationDTO)
        throws URISyntaxException {
        log.debug("REST request to save DeviceInformation : {}", deviceInformationDTO);

        DeviceInformationDTO result = deviceInformationService.saveDeviceInformation(deviceInformationDTO);

        return ResponseEntity
            .created(new URI("/api/device-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /device-informations/:id} : Updates an existing deviceInformation.
     *
     * @param id the id of the deviceInformation to save.
     * @param deviceInformation the deviceInformation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceInformation,
     * or with status {@code 400 (Bad Request)} if the deviceInformation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deviceInformation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/device-informations/{id}")
    public ResponseEntity<DeviceInformation> updateDeviceInformation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DeviceInformation deviceInformation
    ) throws URISyntaxException {
        log.debug("REST request to update DeviceInformation : {}, {}", id, deviceInformation);
        if (deviceInformation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deviceInformation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deviceInformationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DeviceInformation result = deviceInformationService.save(deviceInformation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, deviceInformation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /device-informations/:id} : Partial updates given fields of an existing deviceInformation, field will ignore if it is null
     *
     * @param id the id of the deviceInformation to save.
     * @param deviceInformation the deviceInformation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceInformation,
     * or with status {@code 400 (Bad Request)} if the deviceInformation is not valid,
     * or with status {@code 404 (Not Found)} if the deviceInformation is not found,
     * or with status {@code 500 (Internal Server Error)} if the deviceInformation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/device-informations/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DeviceInformation> partialUpdateDeviceInformation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DeviceInformation deviceInformation
    ) throws URISyntaxException {
        log.debug("REST request to partial update DeviceInformation partially : {}, {}", id, deviceInformation);
        if (deviceInformation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deviceInformation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deviceInformationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DeviceInformation> result = deviceInformationService.partialUpdate(deviceInformation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, deviceInformation.getId().toString())
        );
    }
    @GetMapping("/user/list/device-information")
    public List<DeviceInformation> getListOfDeviceInformationByUserLogin() {
        return deviceInformationService.getListDeviceInformationByUserLogin();
    }

    @GetMapping("/user/device-information/{deviceId}")
    public ResponseEntity<DeviceInformation> getDeviceInformationByUserLoginAndDeviceId(
        @PathVariable String deviceId
    ) {
        log.debug("REST request to get DeviceInformation : {}", deviceId);
        Optional<DeviceInformation> deviceInformation = deviceInformationService.findOneByUserLogin(deviceId);
        return ResponseUtil.wrapOrNotFound(deviceInformation);
    }

    @GetMapping("/admin/list/device-information/{login}")
    public List<DeviceInformation> getListOfDeviceInformationByUserLoginForAdmin(
        @PathVariable String login
    ) {
        return deviceInformationService.getListDeviceInformationByUserLoginForAdmin(login);
    }

    @GetMapping("/admin/device-information/{login}/{deviceId}")
    public ResponseEntity<DeviceInformation> getDeviceInformationByUserLoginAndDeviceIdForAdmin(
        @PathVariable String login, @PathVariable String deviceId
    ) {
        log.debug("REST request to get DeviceInformation by login and deviceId : {}",login, deviceId);
        Optional<DeviceInformation> deviceInformation = deviceInformationService.findOneByUserLoginForAdmin(login,deviceId,"DeviceInformationResource");
        if (!deviceInformation.isPresent()){
            throw new BadRequestAlertException("Device Information not found", ENTITY_NAME,"DeviceInformationNotExist");
        }
        return ResponseUtil.wrapOrNotFound(deviceInformation);
    }

    /**
     * {@code DELETE  /device-informations/:id} : delete the "id" deviceInformation.
     *
     * @param id the id of the deviceInformation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/device-informations/{id}")
    public ResponseEntity<Void> deleteDeviceInformation(@PathVariable Long id) {
        log.debug("REST request to delete DeviceInformation : {}", id);
        deviceInformationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
