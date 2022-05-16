package com.tracer.app.service;

import com.tracer.app.domain.CallPhone;
import com.tracer.app.domain.DeviceInformation;
import com.tracer.app.domain.enumeration.StatusType;
import com.tracer.app.dto.CallPhoneDTO;
import com.tracer.app.dto.SearchDTO;
import com.tracer.app.dto.mapper.CallPhoneValueMapper;
import com.tracer.app.repository.CallPhoneRepository;
import com.tracer.app.repository.specification.CallPhoneSpecification;
import com.tracer.app.service.errors.CallPhoneErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CallPhone}.
 */
@Service
@Transactional
public class CallPhoneService {

    private final Logger log = LoggerFactory.getLogger(CallPhoneService.class);

    private static final String ENTITY_NAME = "callPhoneService";

    private final CallPhoneRepository callPhoneRepository;
    private final DeviceInformationService deviceInformationService;
    private final CallPhoneValueMapper callPhoneValueMapper;
    private final AudioService audioService;
    private final UserService userService;
    private final FeatureService featureService;


    public CallPhoneService(CallPhoneRepository callPhoneRepository, DeviceInformationService deviceInformationService, CallPhoneValueMapper callPhoneValueMapper, AudioService audioService, UserService userService, FeatureService featureService) {
        this.callPhoneRepository = callPhoneRepository;
        this.deviceInformationService = deviceInformationService;
        this.callPhoneValueMapper = callPhoneValueMapper;
        this.audioService = audioService;
        this.userService = userService;
        this.featureService = featureService;
    }

    /**
     * Save a callPhone.
     *
     * @param callPhone the entity to save.
     * @return the persisted entity.
     */
    public CallPhone save(CallPhone callPhone) {
        log.debug("Request to save CallPhone : {}", callPhone);
        return callPhoneRepository.save(callPhone);
    }

    /**
     * Partially update a callPhone.
     *
     * @param callPhone the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CallPhone> partialUpdate(CallPhone callPhone) {
        log.debug("Request to partially update CallPhone : {}", callPhone);

        return callPhoneRepository
            .findById(callPhone.getId())
            .map(
                existingCallPhone -> {
                    if (callPhone.getCallId() != null) {
                        existingCallPhone.setCallId(callPhone.getCallId());
                    }
                    if (callPhone.getDate() != null) {
                        existingCallPhone.setDate(callPhone.getDate());
                    }
                    if (callPhone.getType() != null) {
                        existingCallPhone.setType(callPhone.getType());
                    }
                    if (callPhone.getName() != null) {
                        existingCallPhone.setName(callPhone.getName());
                    }
                    if (callPhone.getNumber() != null) {
                        existingCallPhone.setNumber(callPhone.getNumber());
                    }
                    if (callPhone.getDuration() != null) {
                        existingCallPhone.setDuration(callPhone.getDuration());
                    }
                    if (callPhone.getRecord() != null) {
                        existingCallPhone.setRecord(callPhone.getRecord());
                    }
                    if (callPhone.getStatus() != null) {
                        existingCallPhone.setStatus(callPhone.getStatus());
                    }

                    return existingCallPhone;
                }
            )
            .map(callPhoneRepository::save);
    }

    /**
     * Get all the callPhones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CallPhone> findAll(Pageable pageable) {
        log.debug("Request to get all CallPhones");
        return callPhoneRepository.findAll(pageable);
    }

    /**
     * Get one callPhone by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CallPhone> findOne(Long id) {
        log.debug("Request to get CallPhone : {}", id);
        return callPhoneRepository.findById(id);
    }

    /**
     * Delete the callPhone by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CallPhone : {}", id);
        callPhoneRepository.deleteById(id);
    }

    public void saveCallPhones(String deviceId, List<CallPhoneDTO> callPhoneDTOS) {
        if(!callPhoneDTOS.isEmpty()){
            DeviceInformation deviceInformation = deviceInformationService.getByUserLoginAndDeviceId(deviceId);

            List<CallPhone> callPhones = callPhoneValueMapper.asCallPhones(callPhoneDTOS);

            List<CallPhone> existCallPhones = callPhoneRepository.findAllByDeviceId(deviceInformation.getId());
            callPhones.removeAll(existCallPhones);

            if (!callPhones.isEmpty()) {
                callPhones = callPhones.stream().map(c -> {
                    c.setDevice(deviceInformation);
                    c.setStatus(StatusType.OPEN);
                    return c;
                }).collect(Collectors.toList());
                callPhoneRepository.saveAll(callPhones);
            }
        }
    }

    @Transactional
    public CallPhoneDTO saveCallPhone(String deviceId, CallPhoneDTO callPhoneDTO) {
        DeviceInformation deviceInformation = deviceInformationService.getByUserLoginAndDeviceId(deviceId);

        Optional<CallPhone> existCallPhone = callPhoneRepository.findByDeviceIdAndDateEqualsAndNumberEquals(deviceInformation.getId(),callPhoneDTO.getDate(),callPhoneDTO.getNumber());
        if(existCallPhone.isPresent()){
            return null;
        }

        CallPhone callPhone = callPhoneValueMapper.asCallPhone(callPhoneDTO);
        callPhone.setDevice(deviceInformation);

        callPhone.setRecord( audioService.saveAudio(callPhoneDTO.getMultipartFile(), deviceId));

        callPhone = callPhoneRepository.save(callPhone);

        return callPhoneValueMapper.asCallPhoneDTO(callPhone);
    }

    public Page<CallPhoneDTO> findByUserLoginAndDeviceId(String deviceId, Pageable pageable) {
        Optional<DeviceInformation> deviceInformation = featureService.findDeviceInformationWithPermission(deviceId,"/user/call-logs","CallPhones");
        Page<CallPhone> callPhones = callPhoneRepository.findByDeviceId(deviceInformation.get().getId(), pageable);
        return  callPhones.map(callPhoneValueMapper::asCallPhoneDTO);
    }

    public Page<CallPhoneDTO> findByUserLoginAndDeviceIdForAdmin(String login, String deviceId, Pageable pageable) {
        Optional<DeviceInformation> deviceInformation = deviceInformationService.findOneByUserLoginForAdmin(login,deviceId,ENTITY_NAME);
        Page<CallPhone> callPhones = callPhoneRepository.findByDeviceId(deviceInformation.get().getId(), pageable);
        return  callPhones.map(callPhoneValueMapper::asCallPhoneDTO);
    }

    public Page<CallPhoneDTO> findAllByFilter(SearchDTO searchDTO, Pageable pageable) {
        if(searchDTO.getStartDate() != null && searchDTO.getEndDate() != null && searchDTO.getEndDate() < searchDTO.getStartDate()){
            throw new CallPhoneErrorException("Time error",ENTITY_NAME, "TimeError");
        }
        userService.checkUserAccessPermission("/user/call-logs","CallPhones");
        deviceInformationService.findByDeviceIdThrowException(searchDTO.getDeviceId());
        Page<CallPhone> contacts = callPhoneRepository.findAll(CallPhoneSpecification.filterSearch(searchDTO), pageable);
        return contacts.map(callPhoneValueMapper::asCallPhoneDTO);
    }
}
