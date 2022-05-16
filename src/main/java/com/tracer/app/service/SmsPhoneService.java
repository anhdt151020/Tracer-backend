package com.tracer.app.service;

import com.tracer.app.domain.DeviceInformation;
import com.tracer.app.domain.SmsPhone;
import com.tracer.app.domain.enumeration.StatusType;
import com.tracer.app.dto.NameAndNumberDTO;
import com.tracer.app.dto.SearchDTO;
import com.tracer.app.dto.SmsPhoneDTO;
import com.tracer.app.dto.mapper.SmsPhoneValueMapper;
import com.tracer.app.repository.SmsPhoneRepository;
import com.tracer.app.repository.specification.SmsPhoneSpecification;
import com.tracer.app.service.errors.DeviceInformationErrorException;
import com.tracer.app.service.errors.SmsPhoneErrorException;
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
 * Service Implementation for managing {@link SmsPhone}.
 */
@Service
@Transactional
public class SmsPhoneService {

    private final Logger log = LoggerFactory.getLogger(SmsPhoneService.class);

    private static final String ENTITY_NAME = "smsPhoneService";

    private final SmsPhoneRepository smsPhoneRepository;
    private final DeviceInformationService deviceInformationService;
    private final SmsPhoneValueMapper smsPhoneValueMapper;
    private final UserService userService;
    private final FeatureService featureService;

    public SmsPhoneService(SmsPhoneRepository smsPhoneRepository, DeviceInformationService deviceInformationService, SmsPhoneValueMapper smsPhoneValueMapper, UserService userService, FeatureService featureService) {
        this.smsPhoneRepository = smsPhoneRepository;
        this.deviceInformationService = deviceInformationService;
        this.smsPhoneValueMapper = smsPhoneValueMapper;
        this.userService = userService;
        this.featureService = featureService;
    }

    /**
     * Save a smsPhone.
     *
     * @param smsPhone the entity to save.
     * @return the persisted entity.
     */
    public SmsPhone save(SmsPhone smsPhone) {
        log.debug("Request to save SmsPhone : {}", smsPhone);
        return smsPhoneRepository.save(smsPhone);
    }

    /**
     * Partially update a smsPhone.
     *
     * @param smsPhone the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SmsPhone> partialUpdate(SmsPhone smsPhone) {
        log.debug("Request to partially update SmsPhone : {}", smsPhone);

        return smsPhoneRepository
            .findById(smsPhone.getId())
            .map(
                existingSmsPhone -> {
                    if (smsPhone.getSmsId() != null) {
                        existingSmsPhone.setSmsId(smsPhone.getSmsId());
                    }
                    if (smsPhone.getDate() != null) {
                        existingSmsPhone.setDate(smsPhone.getDate());
                    }
                    if (smsPhone.getType() != null) {
                        existingSmsPhone.setType(smsPhone.getType());
                    }
                    if (smsPhone.getName() != null) {
                        existingSmsPhone.setName(smsPhone.getName());
                    }
                    if (smsPhone.getNumber() != null) {
                        existingSmsPhone.setNumber(smsPhone.getNumber());
                    }
                    if (smsPhone.getData() != null) {
                        existingSmsPhone.setData(smsPhone.getData());
                    }
                    if (smsPhone.getStatus() != null) {
                        existingSmsPhone.setStatus(smsPhone.getStatus());
                    }

                    return existingSmsPhone;
                }
            )
            .map(smsPhoneRepository::save);
    }

    /**
     * Get all the smsPhones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SmsPhone> findAll(Pageable pageable) {
        log.debug("Request to get all SmsPhones");
        return smsPhoneRepository.findAll(pageable);
    }

    /**
     * Get one smsPhone by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SmsPhone> findOne(Long id) {
        log.debug("Request to get SmsPhone : {}", id);
        return smsPhoneRepository.findById(id);
    }

    /**
     * Delete the smsPhone by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SmsPhone : {}", id);
        smsPhoneRepository.deleteById(id);
    }

    public void saveSmsPhones(String deviceId, List<SmsPhoneDTO> smsPhoneDTOS) {
        if(!smsPhoneDTOS.isEmpty()){
            DeviceInformation deviceInformation = deviceInformationService.getByUserLoginAndDeviceId(deviceId);

            List<SmsPhone> smsPhones = smsPhoneValueMapper.asSmsPhones(smsPhoneDTOS);

            List<SmsPhone> existSmsPhones = smsPhoneRepository.findAllByDeviceId(deviceInformation.getId());
            smsPhones.removeAll(existSmsPhones);

            if (!smsPhoneDTOS.isEmpty()) {
                smsPhones = smsPhones.stream().map(s -> {
                    s.setDevice(deviceInformation);
                    s.setStatus(StatusType.OPEN);
                    return s;
                }).collect(Collectors.toList());
                smsPhoneRepository.saveAll(smsPhones);
            }
        }
    }

    @Transactional
    public SmsPhoneDTO saveSmsPhone(String deviceId, SmsPhoneDTO smsPhoneDTO) {
        DeviceInformation deviceInformation = deviceInformationService.getByUserLoginAndDeviceId(deviceId);

        Optional<SmsPhone> existSmsPhone = smsPhoneRepository.findByDeviceIdAndSmsId(deviceInformation.getId(),smsPhoneDTO.getSmsId());
        if(existSmsPhone.isPresent()){
            return null;
        }

        SmsPhone smsPhone = smsPhoneValueMapper.asSmsPhone(smsPhoneDTO);

        smsPhone.setStatus(StatusType.OPEN);
        smsPhone.setDevice(deviceInformation);

        smsPhone = smsPhoneRepository.save(smsPhone);

        return smsPhoneValueMapper.asSmsPhoneDTO(smsPhone);
    }

    public Page<SmsPhoneDTO> findByUserLoginAndDeviceId(String deviceId, Pageable pageable) {
        Optional<DeviceInformation> deviceInformation = featureService.findDeviceInformationWithPermission(deviceId,"/user/sms-phones","SmsPhone");
        Page<SmsPhone> smsPhones = smsPhoneRepository.findByDeviceId(deviceInformation.get().getId(), pageable);
        return smsPhones.map(smsPhoneValueMapper::asSmsPhoneDTO);
    }

    public Page<SmsPhoneDTO> findByUserLoginAndDeviceIdWithNumberPhone(String deviceId, String number, Pageable pageable) {
        Optional<DeviceInformation> deviceInformation = featureService.findDeviceInformationWithPermission(deviceId,"/user/sms-phones","SmsPhone");
        Page<SmsPhone> smsPhones = smsPhoneRepository.findAllByDeviceIdAndAndNumber(deviceInformation.get().getId(),number, pageable);
        return smsPhones.map(smsPhoneValueMapper::asSmsPhoneDTO);
    }

    public Page<NameAndNumberDTO> findAllSmsPhoneContactByUserLoginAndDeviceId(String deviceId, Pageable pageable) {
        Optional<DeviceInformation> deviceInformation = featureService.findDeviceInformationWithPermission(deviceId,"/user/sms-phones","SmsPhone");
        return smsPhoneRepository.getNameAndNumberByDeviceInfoId(deviceInformation.get().getId(),pageable);
    }

    public Page<SmsPhoneDTO> findByUserLoginAndDeviceIdForAdmin(String login, String deviceId, Pageable pageable) {
        Optional<DeviceInformation> deviceInformation = deviceInformationService.findOneByUserLoginForAdmin(login,deviceId,ENTITY_NAME);
        Page<SmsPhone> smsPhones = smsPhoneRepository.findByDeviceId(deviceInformation.get().getId(), pageable);
        return smsPhones.map(smsPhoneValueMapper::asSmsPhoneDTO);
    }

    public Page<SmsPhoneDTO> findByUserLoginAndDeviceIdWithNumberPhoneForAdmin(String login, String deviceId, String number, Pageable pageable) {
        Optional<DeviceInformation> deviceInformation = deviceInformationService.findOneByUserLoginForAdmin(login,deviceId,ENTITY_NAME);
        Page<SmsPhone> smsPhones = smsPhoneRepository.findAllByDeviceIdAndAndNumber(deviceInformation.get().getId(),number, pageable);
        return smsPhones.map(smsPhoneValueMapper::asSmsPhoneDTO);
    }

    public Page<NameAndNumberDTO> findNameAndNumber(SearchDTO searchDTO, Pageable pageable) {
        userService.checkUserAccessPermission("/user/sms-phones","SmsPhone");
        Optional<DeviceInformation> deviceInformation = deviceInformationService.findOneByUserLogin(searchDTO.getDeviceId());
        if (deviceInformation.isEmpty()){
            throw new DeviceInformationErrorException("Device Information not found !!", "SmsPhones", "DeviceInformationNotFound!!");
        }
        return smsPhoneRepository.getNameAndNumberByDeviceInfoIdAndSearch(deviceInformation.get().getId(), searchDTO.likeSearch(),pageable);
    }

    public Page<NameAndNumberDTO> findNameAndNumberForData(SearchDTO searchDTO, Pageable pageable) {
        userService.checkUserAccessPermission("/user/sms-phones","SmsPhone");
        Optional<DeviceInformation> deviceInformation = deviceInformationService.findOneByUserLogin(searchDTO.getDeviceId());
        if (deviceInformation.isEmpty()){
            throw new DeviceInformationErrorException("Device Information not found !!", "SmsPhones", "DeviceInformationNotFound!!");
        }
        return smsPhoneRepository.getNameAndNumberByDeviceInfoIdAndData(deviceInformation.get().getId(), searchDTO.likeSearch(),pageable);
    }

    public Page<SmsPhoneDTO> filterSearch(SearchDTO searchDTO, Pageable pageable) {
        if(searchDTO.getNumber() == null || searchDTO.getNumber().length() == 0){
            throw new SmsPhoneErrorException("Number is not existed", ENTITY_NAME, "NumberNotExisted");
        }
        userService.checkUserAccessPermission("/user/sms-phones","SmsPhone");
        Optional<DeviceInformation> deviceInformation = deviceInformationService.findOneByUserLogin(searchDTO.getDeviceId());
        if (deviceInformation.isEmpty()){
            throw new DeviceInformationErrorException("Device Information not found !!", "SmsPhones", "DeviceInformationNotFound!!");
        }
        Page<SmsPhone> smsPhones = smsPhoneRepository.findAll(SmsPhoneSpecification.filterSearch(searchDTO),pageable);

        return smsPhones.map(smsPhoneValueMapper::asSmsPhoneDTO);
    }

}
