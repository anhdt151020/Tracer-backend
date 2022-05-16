package com.tracer.app.web.rest;

import com.tracer.app.domain.DeviceStatus;
import com.tracer.app.dto.DeviceStatusDTO;
import com.tracer.app.repository.DeviceStatusRepository;
import com.tracer.app.service.DeviceStatusService;
import com.tracer.app.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link DeviceStatus}.
 */
@RestController
@RequestMapping("/api")
public class DeviceStatusResource {

    private final Logger log = LoggerFactory.getLogger(DeviceStatusResource.class);

    private static final String ENTITY_NAME = "deviceStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeviceStatusService deviceStatusService;

    private final DeviceStatusRepository deviceStatusRepository;

    public DeviceStatusResource(DeviceStatusService deviceStatusService, DeviceStatusRepository deviceStatusRepository) {
        this.deviceStatusService = deviceStatusService;
        this.deviceStatusRepository = deviceStatusRepository;
    }

    @PostMapping("/device-statuses/{deviceId}")
    public ResponseEntity<DeviceStatusDTO> createDeviceStatus(@PathVariable("deviceId") String deviceId,@Valid @RequestBody DeviceStatusDTO deviceStatusDTO) throws URISyntaxException {
        log.debug("REST request to save DeviceStatus : {}",deviceId, deviceStatusDTO);

        DeviceStatusDTO result = deviceStatusService.saveDeviceStatus(deviceId,deviceStatusDTO);

        return ResponseEntity
            .created(new URI("/api/device-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /device-statuses/:id} : Updates an existing deviceStatus.
     *
     * @param id the id of the deviceStatus to save.
     * @param deviceStatus the deviceStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceStatus,
     * or with status {@code 400 (Bad Request)} if the deviceStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deviceStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/device-statuses/{id}")
    public ResponseEntity<DeviceStatus> updateDeviceStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeviceStatus deviceStatus
    ) throws URISyntaxException {
        log.debug("REST request to update DeviceStatus : {}, {}", id, deviceStatus);
        if (deviceStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deviceStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deviceStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DeviceStatus result = deviceStatusService.save(deviceStatus);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, deviceStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /device-statuses/:id} : Partial updates given fields of an existing deviceStatus, field will ignore if it is null
     *
     * @param id the id of the deviceStatus to save.
     * @param deviceStatus the deviceStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceStatus,
     * or with status {@code 400 (Bad Request)} if the deviceStatus is not valid,
     * or with status {@code 404 (Not Found)} if the deviceStatus is not found,
     * or with status {@code 500 (Internal Server Error)} if the deviceStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/device-statuses/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DeviceStatus> partialUpdateDeviceStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeviceStatus deviceStatus
    ) throws URISyntaxException {
        log.debug("REST request to partial update DeviceStatus partially : {}, {}", id, deviceStatus);
        if (deviceStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deviceStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deviceStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DeviceStatus> result = deviceStatusService.partialUpdate(deviceStatus);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, deviceStatus.getId().toString())
        );
    }

    @GetMapping("/user/device-statuses/{deviceId}")
    public DeviceStatusDTO getDeviceStatusByLoginAndDeviceId(
        @PathVariable String deviceId
    ) {
        log.debug("REST request to get DeviceStatus : {}", deviceId);
        DeviceStatusDTO deviceStatusDTO = deviceStatusService.findOneByLoginAndDeviceId(deviceId);
        return deviceStatusDTO;
    }

    @GetMapping("/admin/device-statuses/{login}/{deviceId}")
    public DeviceStatusDTO getDeviceStatusByLoginAndDeviceIdForAdmin(
        @PathVariable String login, @PathVariable String deviceId
    ) {
        log.debug("REST request to get DeviceStatus : {}",login, deviceId);
        DeviceStatusDTO deviceStatusDTO = deviceStatusService.findOneByLoginAndDeviceIdForAdmin(login,deviceId);
        return deviceStatusDTO;
    }

    /**
     * {@code DELETE  /device-statuses/:id} : delete the "id" deviceStatus.
     *
     * @param id the id of the deviceStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/device-statuses/{id}")
    public ResponseEntity<Void> deleteDeviceStatus(@PathVariable Long id) {
        log.debug("REST request to delete DeviceStatus : {}", id);
        deviceStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
