package com.tracer.app.service;

import com.tracer.app.domain.DeviceInformation;
import com.tracer.app.domain.User;
import com.tracer.app.dto.DeviceInformationDTO;
import com.tracer.app.dto.mapper.DeviceInformationValueMapper;
import com.tracer.app.repository.DeviceInformationRepository;
import com.tracer.app.service.errors.DeviceInformationErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service Implementation for managing {@link DeviceInformation}.
 */
@Service
@Transactional
public class DeviceInformationService {

    private final Logger log = LoggerFactory.getLogger(DeviceInformationService.class);

    private static final String ENTITY_NAME = "deviceInformationService";

    private final DeviceInformationRepository deviceInformationRepository;
    private final UserService userService;
    private final DeviceService deviceService;
    private final DeviceInformationValueMapper deviceInformationValueMapper;

    public DeviceInformationService(DeviceInformationRepository deviceInformationRepository, UserService userService, DeviceService deviceService, DeviceInformationValueMapper deviceInformationValueMapper) {
        this.deviceInformationRepository = deviceInformationRepository;
        this.userService = userService;
        this.deviceService = deviceService;
        this.deviceInformationValueMapper = deviceInformationValueMapper;
    }

    /**
     * Save a deviceInformation.
     *
     * @param deviceInformation the entity to save.
     * @return the persisted entity.
     */
    public DeviceInformation save(DeviceInformation deviceInformation) {
        log.debug("Request to save DeviceInformation : {}", deviceInformation);
        return deviceInformationRepository.save(deviceInformation);
    }

    /**
     * Partially update a deviceInformation.
     *
     * @param deviceInformation the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DeviceInformation> partialUpdate(DeviceInformation deviceInformation) {
        log.debug("Request to partially update DeviceInformation : {}", deviceInformation);

        return deviceInformationRepository
            .findById(deviceInformation.getId())
            .map(
                existingDeviceInformation -> {
                    if (deviceInformation.getName() != null) {
                        existingDeviceInformation.setName(deviceInformation.getName());
                    }
                    if (deviceInformation.getModel() != null) {
                        existingDeviceInformation.setModel(deviceInformation.getModel());
                    }
                    if (deviceInformation.getVersion() != null) {
                        existingDeviceInformation.setVersion(deviceInformation.getVersion());
                    }
                    if (deviceInformation.getDeviceId() != null) {
                        existingDeviceInformation.setDeviceId(deviceInformation.getDeviceId());
                    }
                    if (deviceInformation.getCellNumber() != null) {
                        existingDeviceInformation.setCellNumber(deviceInformation.getCellNumber());
                    }
                    if (deviceInformation.getOperator() != null) {
                        existingDeviceInformation.setOperator(deviceInformation.getOperator());
                    }
                    if (deviceInformation.getInternalStorage() != null) {
                        existingDeviceInformation.setInternalStorage(deviceInformation.getInternalStorage());
                    }
                    if (deviceInformation.getExternalStorage() != null) {
                        existingDeviceInformation.setExternalStorage(deviceInformation.getExternalStorage());
                    }
                    if (deviceInformation.getMemory() != null) {
                        existingDeviceInformation.setMemory(deviceInformation.getMemory());
                    }
                    if (deviceInformation.getCreated() != null) {
                        existingDeviceInformation.setCreated(deviceInformation.getCreated());
                    }
                    if (deviceInformation.getModified() != null) {
                        existingDeviceInformation.setModified(deviceInformation.getModified());
                    }
                    if (deviceInformation.getStatus() != null) {
                        existingDeviceInformation.setStatus(deviceInformation.getStatus());
                    }

                    return existingDeviceInformation;
                }
            )
            .map(deviceInformationRepository::save);
    }

    /**
     * Get all the deviceInformations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DeviceInformation> findAll(Pageable pageable) {
        log.debug("Request to get all DeviceInformations");
        return deviceInformationRepository.findAll(pageable);
    }

    /**
     * Get one deviceInformation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DeviceInformation> findOne(Long id) {
        log.debug("Request to get DeviceInformation : {}", id);
        return deviceInformationRepository.findById(id);
    }

    /**
     * Delete the deviceInformation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DeviceInformation : {}", id);
        deviceInformationRepository.deleteById(id);
    }

    public DeviceInformation findByDeviceId(String deviceId) {
        Optional<DeviceInformation> deviceInformation = deviceInformationRepository.findByDeviceId(deviceId);
        if(deviceInformation.isPresent()){
            return deviceInformation.get();
        }

        return null;
    }

    public DeviceInformation findByDeviceIdIsNullThenSave(String deviceId) {
        DeviceInformation deviceInformation = findByDeviceId(deviceId);
        if (deviceInformation == null){
            deviceInformation = new DeviceInformation();
            deviceInformation.setDeviceId(deviceId);

            deviceInformation = deviceInformationRepository.save(deviceInformation);
        }

        return deviceInformation;
    }

    public DeviceInformation findByDeviceIdThrowException(String deviceId) {
        DeviceInformation deviceInformation = findByDeviceId(deviceId);
        if (deviceInformation == null){
            throw new DeviceInformationErrorException("DeviceInformation is not existed",ENTITY_NAME, "DeviceInformationNotExisted");
        }

        return deviceInformation;
    }

    public DeviceInformation getByUserLoginAndDeviceId(String deviceId) {
        User user = userService.getUserLoginHasException();
        deviceService.findByUserIdAndDeviceIdIsNullThenSave(user,deviceId);
        return findByDeviceIdIsNullThenSave(deviceId);
    }

    public DeviceInformation getByUserLoginAndDeviceIdThrowExcetion(String deviceId) {
        User user = userService.getUserLoginHasException();
        deviceService.findByUserIdAndDeviceIdThrowException(user,deviceId);
        return findByDeviceIdIsNullThenSave(deviceId);
    }

    public DeviceInformationDTO saveDeviceInformation(DeviceInformationDTO deviceInformationDTO) {
        User user = userService.getUserLoginHasException();
        deviceService.findByUserIdAndDeviceIdIsNullThenSave(user,deviceInformationDTO.getDeviceId());

        DeviceInformation deviceInformation = findByDeviceId(deviceInformationDTO.getDeviceId());
        if(deviceInformation == null){
            deviceInformation = deviceInformationValueMapper.asDeviceInformation(deviceInformationDTO);
        }else{
            Long id = deviceInformation.getId();
            Long created = deviceInformation.getCreated();

            deviceInformation = deviceInformationValueMapper.asDeviceInformationCustom(deviceInformationDTO);
            deviceInformation.setId(id);
            deviceInformation.setCreated(created);
            deviceInformation.setModified(Instant.now().getEpochSecond());
        }

        return deviceInformationValueMapper.asDeviceInformationDTO(deviceInformationRepository.save(deviceInformation));
    }


    public List<DeviceInformation> getListDeviceInformationByUserLogin(){
        User user = userService.getUserLoginHasException();
        List<String> deviceIds = deviceService.findDeviceIdsByUserId(user.getId());
        return deviceInformationRepository.findAllByDeviceIdIn(deviceIds);
    }

    public Optional<DeviceInformation> findOneByUserLogin(String deviceId) {
        List<DeviceInformation> deviceInformations = getListDeviceInformationByUserLogin();
        return deviceInformations.stream().
            filter(s-> Objects.equals(s.getDeviceId(), deviceId)).findFirst();
    }

    /**
     * FOR ADMIN REQUEST
     */

    public List<DeviceInformation> getListDeviceInformationByUserLoginForAdmin(String login){
        User user = userService.getUserByLoginForAdmin(login);
        List<String> deviceIds = deviceService.findDeviceIdsByUserId(user.getId());
        return deviceInformationRepository.findAllByDeviceIdIn(deviceIds);
    }

    public Optional<DeviceInformation> findOneByUserLoginForAdmin(String login, String deviceId,String entityName) {
        List<DeviceInformation> deviceInformations = getListDeviceInformationByUserLoginForAdmin(login);
        Optional<DeviceInformation> deviceInformation = deviceInformations.stream().filter(s-> Objects.equals(s.getDeviceId(), deviceId)).findFirst();
        if (deviceInformation.isEmpty()){
            throw new DeviceInformationErrorException("Device Information not found !!", entityName, "DeviceInformationNotFound!!");
        }
        return deviceInformation;
    }
}
