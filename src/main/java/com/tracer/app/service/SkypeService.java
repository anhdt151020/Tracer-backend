package com.tracer.app.service;

import com.tracer.app.domain.DeviceInformation;
import com.tracer.app.domain.Skype;
import com.tracer.app.dto.SocialNetworkDTO;
import com.tracer.app.dto.mapper.SocialNetworkMapper;
import com.tracer.app.repository.SkypeRepository;
import com.tracer.app.service.errors.DeviceInformationErrorException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SkypeService {
    private final Logger log = LoggerFactory.getLogger(SkypeService.class);

    private static final String ENTITY_NAME = "SkypeService";

    private final SkypeRepository skypeRepository;
    private final UserService userService;
    private final DeviceInformationService deviceInformationService;
    private final SocialNetworkMapper socialNetworkMapper;
    private final FeatureService featureService;

    public SkypeService(SkypeRepository skypeRepository, UserService userService, DeviceInformationService deviceInformationService, SocialNetworkMapper socialNetworkMapper, FeatureService featureService) {
        this.skypeRepository = skypeRepository;
        this.userService = userService;
        this.deviceInformationService = deviceInformationService;
        this.socialNetworkMapper = socialNetworkMapper;
        this.featureService = featureService;
    }

    public Page<SocialNetworkDTO> findByUserLoginAndDeviceId(String deviceId, Pageable pageable) {
        Optional<DeviceInformation> deviceInformation = featureService.findDeviceInformationWithPermission(deviceId,"/user/skypes", "Skype");
        Page<Skype> skypes = skypeRepository.findSkypeByDeviceId(deviceInformation.get().getId(), pageable);
        return skypes.map(socialNetworkMapper::asSocialNetworkGetDataDTOForSkype);
    }

    public Page<SocialNetworkDTO> findByUserLoginAndDeviceIdForAdmin(String login, String deviceId, Pageable pageable) {
        Optional<DeviceInformation> deviceInformation = deviceInformationService.findOneByUserLoginForAdmin(login,deviceId,ENTITY_NAME);
        Page<Skype> skypes = skypeRepository.findSkypeByDeviceId(deviceInformation.get().getId(), pageable);
        return  skypes.map(socialNetworkMapper::asSocialNetworkGetDataDTOForSkype);
    }

    public Skype save(Skype skype) {
        log.debug("Request to save Skype : {}", skype);
        return skypeRepository.save(skype);
    }

    public Optional<Skype> findSkypeByContactAndDate(String contact, Long date) {
        return skypeRepository.findSkypeByContactAndDate(contact,date);
    }
}
