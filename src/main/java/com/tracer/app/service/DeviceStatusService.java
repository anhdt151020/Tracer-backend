package com.tracer.app.service;

import com.tracer.app.domain.DeviceInformation;
import com.tracer.app.domain.DeviceStatus;
import com.tracer.app.dto.DeviceStatusDTO;
import com.tracer.app.dto.mapper.DeviceStatusValueMapper;
import com.tracer.app.repository.DeviceStatusRepository;
import com.tracer.app.service.errors.DeviceInformationErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link DeviceStatus}.
 */
@Service
@Transactional
public class DeviceStatusService {

    private final Logger log = LoggerFactory.getLogger(DeviceStatusService.class);

    private static final String ENTITY_NAME = "deviceStatusService";

    private final DeviceStatusRepository deviceStatusRepository;
    private final DeviceInformationService deviceInformationDTO;
    private final DeviceStatusValueMapper deviceStatusValueMapper;
    private final DeviceInformationService deviceInformationService;

    public DeviceStatusService(DeviceStatusRepository deviceStatusRepository, DeviceInformationService deviceInformationDTO, DeviceStatusValueMapper deviceStatusValueMapper, DeviceInformationService deviceInformationService) {
        this.deviceStatusRepository = deviceStatusRepository;
        this.deviceInformationDTO = deviceInformationDTO;
        this.deviceStatusValueMapper = deviceStatusValueMapper;
        this.deviceInformationService = deviceInformationService;
    }

    /**
     * Save a deviceStatus.
     *
     * @param deviceStatus the entity to save.
     * @return the persisted entity.
     */
    public DeviceStatus save(DeviceStatus deviceStatus) {
        log.debug("Request to save DeviceStatus : {}", deviceStatus);
        return deviceStatusRepository.save(deviceStatus);
    }

    /**
     * Partially update a deviceStatus.
     *
     * @param deviceStatus the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DeviceStatus> partialUpdate(DeviceStatus deviceStatus) {
        log.debug("Request to partially update DeviceStatus : {}", deviceStatus);

        return deviceStatusRepository
            .findById(deviceStatus.getId())
            .map(
                existingDeviceStatus -> {
                    if (deviceStatus.getBattery() != null) {
                        existingDeviceStatus.setBattery(deviceStatus.getBattery());
                    }
                    if (deviceStatus.getMemory() != null) {
                        existingDeviceStatus.setMemory(deviceStatus.getMemory());
                    }
                    if (deviceStatus.getInternalStorage() != null) {
                        existingDeviceStatus.setInternalStorage(deviceStatus.getInternalStorage());
                    }
                    if (deviceStatus.getExternalStorage() != null) {
                        existingDeviceStatus.setExternalStorage(deviceStatus.getExternalStorage());
                    }
                    if (deviceStatus.getIsWifi() != null) {
                        existingDeviceStatus.setIsWifi(deviceStatus.getIsWifi());
                    }
                    if (deviceStatus.getIsSimInternet() != null) {
                        existingDeviceStatus.setIsSimInternet(deviceStatus.getIsSimInternet());
                    }
                    if (deviceStatus.getIsGPS() != null) {
                        existingDeviceStatus.setIsGPS(deviceStatus.getIsGPS());
                    }
                    if (deviceStatus.getCreated() != null) {
                        existingDeviceStatus.setCreated(deviceStatus.getCreated());
                    }
                    if (deviceStatus.getModified() != null) {
                        existingDeviceStatus.setModified(deviceStatus.getModified());
                    }

                    return existingDeviceStatus;
                }
            )
            .map(deviceStatusRepository::save);
    }

    /**
     * Get all the deviceStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DeviceStatus> findAll(Pageable pageable) {
        log.debug("Request to get all DeviceStatuses");
        return deviceStatusRepository.findAll(pageable);
    }

    /**
     *  Get all the deviceStatuses where DeviceInformation is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DeviceStatus> findAllWhereDeviceInformationIsNull() {
        log.debug("Request to get all deviceStatuses where DeviceInformation is null");
        return StreamSupport
            .stream(deviceStatusRepository.findAll().spliterator(), false)
            .filter(deviceStatus -> deviceStatus.getDeviceInformation() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one deviceStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DeviceStatus> findOne(Long id) {
        log.debug("Request to get DeviceStatus : {}", id);
        return deviceStatusRepository.findById(id);
    }

    /**
     * Delete the deviceStatus by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DeviceStatus : {}", id);
        deviceStatusRepository.deleteById(id);
    }

    public DeviceStatusDTO saveDeviceStatus(String deviceId, DeviceStatusDTO deviceStatusDTO) {
        DeviceInformation deviceInformation = deviceInformationDTO.findByDeviceIdIsNullThenSave(deviceId);

        Optional<DeviceStatus> deviceStatus = deviceStatusRepository.findByDeviceInformationId(deviceInformation.getId());
        DeviceStatus save = deviceStatusValueMapper.asDeviceStatus(deviceStatusDTO);
        if(deviceStatus.isPresent()){
            save.setId(deviceStatus.get().getId());
            save.setCreated(deviceStatus.get().getCreated());
            save.setModified(Instant.now().getEpochSecond());
            save.setDeviceInformation(deviceInformation);
        }else{
            save.setCreated(Instant.now().getEpochSecond());
            save.setDeviceInformation(deviceInformation);
        }

        return deviceStatusValueMapper.asDeviceStatusDTO(deviceStatusRepository.save(save));
    }

    public Optional<DeviceStatus> findOneByDeviceId(Long id){
        Optional<DeviceStatus> deviceStatus = deviceStatusRepository.findByDeviceInformationId(id);
        if (deviceStatus.isEmpty()){
            throw new DeviceInformationErrorException("Device Information has 0 record of status", ENTITY_NAME,"Status not found!!");
        }
        return deviceStatus;
    }

    public DeviceStatusDTO findOneByLoginAndDeviceId(String deviceId) {
        DeviceInformation deviceInformation = deviceInformationService.getByUserLoginAndDeviceId(deviceId);
        Optional<DeviceStatus> deviceStatus = findOneByDeviceId(deviceInformation.getId());
        DeviceStatusDTO deviceStatusDTO = deviceStatusValueMapper.asDeviceStatusDTO(deviceStatus.get());
        return deviceStatusDTO;
    }

    public DeviceStatusDTO findOneByLoginAndDeviceIdForAdmin(String login, String deviceId) {
        Optional<DeviceInformation> deviceInformation = deviceInformationService.findOneByUserLoginForAdmin(login,deviceId,ENTITY_NAME);
        Optional<DeviceStatus> deviceStatus = findOneByDeviceId(deviceInformation.get().getId());
        DeviceStatusDTO deviceStatusDTO = deviceStatusValueMapper.asDeviceStatusDTO(deviceStatus.get());
        return deviceStatusDTO;
    }
}
