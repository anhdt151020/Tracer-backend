package com.tracer.app.service;

import com.tracer.app.domain.DeviceInformation;
import com.tracer.app.domain.Zalo;
import com.tracer.app.dto.SocialNetworkDTO;
import com.tracer.app.dto.mapper.SocialNetworkMapper;
import com.tracer.app.repository.ZaloRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ZaloService {
    private final Logger log = LoggerFactory.getLogger(ZaloService.class);

    private static final String ENTITY_NAME = "zaloService";

    private final ZaloRepository zaloRepository;
    private final DeviceInformationService deviceInformationService;
    private final SocialNetworkMapper socialNetworkMapper;
    private final UserService userService;
    private final FeatureService featureService;

    public ZaloService(ZaloRepository zaloRepository, DeviceInformationService deviceInformationService, SocialNetworkMapper socialNetworkMapper, UserService userService, FeatureService featureService) {
        this.zaloRepository = zaloRepository;
        this.deviceInformationService = deviceInformationService;
        this.socialNetworkMapper = socialNetworkMapper;
        this.userService = userService;
        this.featureService = featureService;
    }

    public Page<SocialNetworkDTO> findByUserLoginAndDeviceId(String deviceId, Pageable pageable) {
        userService.checkUserAccessPermission("/user/zalos", "Zalo");
        Optional<DeviceInformation> deviceInformation = featureService.findDeviceInformationWithPermission(deviceId,"/user/zalos", "Zalo");
        Page<Zalo> zalos = zaloRepository.findZaloByDeviceId(deviceInformation.get().getId(), pageable);
        return zalos.map(socialNetworkMapper::asSocialNetworkGetDataDTOForZalo);
    }

    public Page<SocialNetworkDTO> findByUserLoginAndDeviceIdForAdmin(String login, String deviceId, Pageable pageable) {
        Optional<DeviceInformation> deviceInformation = deviceInformationService.findOneByUserLoginForAdmin(login,deviceId,ENTITY_NAME);
        Page<Zalo> zalos = zaloRepository.findZaloByDeviceId(deviceInformation.get().getId(), pageable);
        return  zalos.map(socialNetworkMapper::asSocialNetworkGetDataDTOForZalo);
    }

    public Zalo save(Zalo zalo) {
        log.debug("Request to save Zalo : {}", zalo);
        return zaloRepository.save(zalo);
    }

    public Optional<Zalo> findZaloByContactAndDate(String contact, Long date) {
        return zaloRepository.findZaloByContactAndDate(contact,date);
    }
}
