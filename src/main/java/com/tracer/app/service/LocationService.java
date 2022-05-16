package com.tracer.app.service;

import com.tracer.app.domain.*;
import com.tracer.app.dto.LocationDTO;
import com.tracer.app.dto.SearchDTO;
import com.tracer.app.dto.mapper.LocationValueMapper;
import com.tracer.app.repository.LocationRepository;
import com.tracer.app.repository.specification.LocationSpecification;
import com.tracer.app.service.errors.LocationErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Location}.
 */
@Service
@Transactional
public class LocationService {

    private final Logger log = LoggerFactory.getLogger(LocationService.class);

    private static final String ENTITY_NAME = "locationService";

    private final LocationRepository locationRepository;
    private final DeviceInformationService deviceInformationService;
    private final LocationValueMapper locationValueMapper;
    private final UserService userService;
    private final FeatureService featureService;

    public LocationService(LocationRepository locationRepository, DeviceInformationService deviceInformationService, LocationValueMapper locationValueMapper, UserService userService, FeatureService featureService) {
        this.locationRepository = locationRepository;
        this.deviceInformationService = deviceInformationService;
        this.locationValueMapper = locationValueMapper;
        this.userService = userService;
        this.featureService = featureService;
    }

    /**
     * Save a location.
     *
     * @param location the entity to save.
     * @return the persisted entity.
     */
    public Location save(Location location) {
        log.debug("Request to save Location : {}", location);
        return locationRepository.save(location);
    }

    /**
     * Partially update a location.
     *
     * @param location the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Location> partialUpdate(Location location) {
        log.debug("Request to partially update Location : {}", location);

        return locationRepository
            .findById(location.getId())
            .map(existingLocation -> {
                if (location.getLongitude() != null) {
                    existingLocation.setLongitude(location.getLongitude());
                }
                if (location.getLatitude() != null) {
                    existingLocation.setLatitude(location.getLatitude());
                }
                if (location.getTime() != null) {
                    existingLocation.setTime(location.getTime());
                }

                return existingLocation;
            })
            .map(locationRepository::save);
    }

    /**
     * Get all the locations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Location> findAll(Pageable pageable) {
        log.debug("Request to get all Locations");
        return locationRepository.findAll(pageable);
    }

    /**
     * Get one location by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Location> findOne(Long id) {
        log.debug("Request to get Location : {}", id);
        return locationRepository.findById(id);
    }

    /**
     * Delete the location by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Location : {}", id);
        locationRepository.deleteById(id);
    }

    public void saveLocations(String deviceId, List<LocationDTO> locationDTOs) {
        log.debug("Request to save Locations : {}",deviceId ,locationDTOs);

        DeviceInformation deviceInformation = deviceInformationService.getByUserLoginAndDeviceId(deviceId);

        if(!locationDTOs.isEmpty()) {
            Optional<LocationDTO> locationDTO = locationDTOs.stream().min((o1, o2) -> Long.compare(o1.getTime(), o2.getTime()));
            if(locationDTO.isPresent()){
                Pageable page = PageRequest.of(0, 1, Sort.by("time").descending());
                List<Location> timeLocations = locationRepository.findAllByTimeAndDeviceId(locationDTO.get().getTime(),deviceInformation.getId(), page).toList();

                List<Location> locations = locationValueMapper.asLocations(locationDTOs);
                if (!timeLocations.isEmpty()) {
                    Location finalLocation = timeLocations.get(0);
                    locations = locations.stream().filter(l -> {
                        l.setDevice(deviceInformation);
                        return l.getTime().longValue() > finalLocation.getTime().longValue();
                    }).collect(Collectors.toList());
                }else{
                    locations = locations.stream().map(l -> {
                        l.setDevice(deviceInformation);
                        return l;
                    }).collect(Collectors.toList());
                }
                if(!locations.isEmpty()){
                    locationRepository.saveAll(locations);
                }
            }
        }
    }

    public Page<LocationDTO> findByUserLoginAndDeviceId(String deviceId, Pageable pageable) {
        Optional<DeviceInformation> deviceInformation = featureService.findDeviceInformationWithPermission(deviceId,"/user/location","Locations");
        Page<Location> locations = locationRepository.findByDeviceId(deviceInformation.get().getId(), pageable);
        return locations.map(locationValueMapper::asLocationDto);
    }

    public Page<LocationDTO> findByUserLoginAndDeviceIdForAdmin(String login, String deviceId, Pageable pageable) {
        Optional<DeviceInformation> deviceInformation = deviceInformationService.findOneByUserLoginForAdmin(login,deviceId,ENTITY_NAME);
        Page<Location> locations = locationRepository.findByDeviceId(deviceInformation.get().getId(), pageable);
        return locations.map(locationValueMapper::asLocationDto);
    }

    public Page<LocationDTO> findAllByFilter(SearchDTO searchDTO, Pageable pageable) {
        if(searchDTO.getStartDate() != null && searchDTO.getEndDate() != null && searchDTO.getStartDate()> searchDTO.getEndDate()){
            throw new LocationErrorException("Time is error" , ENTITY_NAME, "TimeError");
        }
        userService.checkUserAccessPermission("/user/location","Locations");
        deviceInformationService.findByDeviceIdThrowException(searchDTO.getDeviceId());
        Page<Location> contacts = locationRepository.findAll(LocationSpecification.filterSearch(searchDTO), pageable);
        return contacts.map(locationValueMapper::asLocationDto);
    }
}
