package com.tracer.app.web.rest.oldResource;

import com.tracer.app.domain.DeviceStatus;
import com.tracer.app.repository.DeviceStatusRepository;
import com.tracer.app.service.DeviceStatusService;
import com.tracer.app.web.rest.DeviceStatusResource;
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
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/old")
public class OldDeviceStatusResource {
    private final Logger log = LoggerFactory.getLogger(DeviceStatusResource.class);

    private static final String ENTITY_NAME = "deviceStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeviceStatusService deviceStatusService;

    private final DeviceStatusRepository deviceStatusRepository;

    public OldDeviceStatusResource(DeviceStatusService deviceStatusService, DeviceStatusRepository deviceStatusRepository) {
        this.deviceStatusService = deviceStatusService;
        this.deviceStatusRepository = deviceStatusRepository;
    }

    /**
     * {@code GET  /device-statuses} : get all the deviceStatuses.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deviceStatuses in body.
     */
    @GetMapping("/device-statuses")
    public ResponseEntity<List<DeviceStatus>> getAllDeviceStatuses(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("deviceinformation-is-null".equals(filter)) {
            log.debug("REST request to get all DeviceStatuss where deviceInformation is null");
            return new ResponseEntity<>(deviceStatusService.findAllWhereDeviceInformationIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of DeviceStatuses");
        Page<DeviceStatus> page = deviceStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /device-statuses/:id} : get the "id" deviceStatus.
     *
     * @param id the id of the deviceStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deviceStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/device-statuses/{id}")
    public ResponseEntity<DeviceStatus> getDeviceStatus(@PathVariable Long id) {
        log.debug("REST request to get DeviceStatus : {}", id);
        Optional<DeviceStatus> deviceStatus = deviceStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deviceStatus);
    }

}
