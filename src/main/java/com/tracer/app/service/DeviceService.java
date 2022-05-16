package com.tracer.app.service;

import com.tracer.app.domain.Device;
import com.tracer.app.domain.User;
import com.tracer.app.domain.enumeration.StatusType;
import com.tracer.app.repository.DeviceRepository;
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

/**
 * Service Implementation for managing {@link Device}.
 */
@Service
@Transactional
public class DeviceService {

    private final Logger log = LoggerFactory.getLogger(DeviceService.class);

    private static final String ENTITY_NAME = "deviceService";

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    /**
     * Save a device.
     *
     * @param device the entity to save.
     * @return the persisted entity.
     */
    public Device save(Device device) {
        log.debug("Request to save Device : {}", device);
        return deviceRepository.save(device);
    }

    /**
     * Partially update a device.
     *
     * @param device the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Device> partialUpdate(Device device) {
        log.debug("Request to partially update Device : {}", device);

        return deviceRepository
            .findById(device.getId())
            .map(
                existingDevice -> {
                    if (device.getDeviceId() != null) {
                        existingDevice.setDeviceId(device.getDeviceId());
                    }
                    if (device.getStatus() != null) {
                        existingDevice.setStatus(device.getStatus());
                    }
                    if (device.getCreated() != null) {
                        existingDevice.setCreated(device.getCreated());
                    }
                    if (device.getModified() != null) {
                        existingDevice.setModified(device.getModified());
                    }

                    return existingDevice;
                }
            )
            .map(deviceRepository::save);
    }

    /**
     * Get all the devices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Device> findAll(Pageable pageable) {
        log.debug("Request to get all Devices");
        return deviceRepository.findAll(pageable);
    }

    /**
     * Get one device by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Device> findOne(Long id) {
        log.debug("Request to get Device : {}", id);
        return deviceRepository.findById(id);
    }

    /**
     * Delete the device by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Device : {}", id);
        deviceRepository.deleteById(id);
    }

    public Device findByUserIdAndDeviceId(User user, String deviceId) {
        Optional<Device> device = deviceRepository.findByUserIdAndDeviceId(user.getId(),deviceId);
        if(device.isPresent()){
            return device.get();
        }
        return null;
    }

    public Device findByUserIdAndDeviceIdIsNullThenSave(User user, String deviceId){
        Device device = findByUserIdAndDeviceId(user,deviceId);
        if(device == null){
            device = new Device();
            device.setDeviceId(deviceId);
            device.setUser(user);
            device.setCreated(Instant.now().getEpochSecond());
            device.setStatus(StatusType.ACTIVE);

            device = deviceRepository.save(device);
        }

        return device;
    }

    public Device findByUserIdAndDeviceIdThrowException(User user, String deviceId){
        Device device = findByUserIdAndDeviceId(user,deviceId);
        if(device == null){
            throw new DeviceInformationErrorException("Device is not existed",ENTITY_NAME, "DeviceNotExisted");
        }

        return device;
    }

    public List<String> findDeviceIdsByUserId(Long id) {
        List<Device> devices = deviceRepository.findByUserId(id);
        List<String> ids = devices.stream().map(Device::getDeviceId).collect(Collectors.toList());
        if (ids.isEmpty()){
            return null;
        }
        return ids;
    }
}
