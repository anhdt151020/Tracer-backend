package com.tracer.app.web.rest;

import com.tracer.app.domain.Location;
import com.tracer.app.dto.ContactDTO;
import com.tracer.app.dto.LocationDTO;
import com.tracer.app.dto.SearchDTO;
import com.tracer.app.repository.LocationRepository;
import com.tracer.app.service.LocationService;
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
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link Location}.
 */
@RestController
@RequestMapping("/api")
public class LocationResource {

    private final Logger log = LoggerFactory.getLogger(LocationResource.class);

    private static final String ENTITY_NAME = "location";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocationService locationService;

    private final LocationRepository locationRepository;

    public LocationResource(LocationService locationService, LocationRepository locationRepository) {
        this.locationService = locationService;
        this.locationRepository = locationRepository;
    }

    /**
     * {@code POST  /locations} : Create a new location.
     *
     * @param deviceId,locationDTOS the location to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locations.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/locations/list/{deviceId}")
    public void createLocations(@PathVariable("deviceId") String deviceId,@RequestBody List<LocationDTO> locationDTOS) throws URISyntaxException {
        log.debug("REST request to save Locations : {}", deviceId, locationDTOS);
        locationService.saveLocations(deviceId,locationDTOS);

        log.info("Success");
    }

    @PostMapping("/user/locations/search/{deviceId}")
    public ResponseEntity<List<LocationDTO>> searchLocations(@PathVariable("deviceId") String deviceId, @Valid @RequestBody SearchDTO searchDTO, Pageable pageable) {
        searchDTO.setDeviceId(deviceId);
        Page<LocationDTO> page = locationService.findAllByFilter(searchDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/user/location/{deviceId}")
    public ResponseEntity<List<LocationDTO>> getAllCallPhoneByUserLoginAndDeviceId(
        @PathVariable String deviceId, Pageable pageable
    ) {
        Page<LocationDTO> page = locationService.findByUserLoginAndDeviceId(deviceId,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/admin/location/{login}/{deviceId}")
    public ResponseEntity<List<LocationDTO>> getAllCallPhoneByUserLoginAndDeviceIdForAdmin(
        @PathVariable String login, @PathVariable String deviceId, Pageable pageable
    ) {
        Page<LocationDTO> page = locationService.findByUserLoginAndDeviceIdForAdmin(login,deviceId,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code DELETE  /locations/:id} : delete the "id" location.
     *
     * @param id the id of the location to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/locations/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        log.debug("REST request to delete Location : {}", id);
        locationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
