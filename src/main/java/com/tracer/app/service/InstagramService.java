package com.tracer.app.service;

import com.tracer.app.domain.DeviceInformation;
import com.tracer.app.domain.Instagram;
import com.tracer.app.dto.SocialNetworkDTO;
import com.tracer.app.dto.mapper.SocialNetworkMapper;
import com.tracer.app.repository.InstagramRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class InstagramService {
    private final Logger log = LoggerFactory.getLogger(InstagramService.class);

    private static final String ENTITY_NAME = "instagramService";

    private final InstagramRepository instagramRepository;
    private final DeviceInformationService deviceInformationService;
    private final SocialNetworkMapper socialNetworkMapper;
    private final FeatureService featureService;

    public InstagramService(InstagramRepository instagramRepository, DeviceInformationService deviceInformationService, SocialNetworkMapper socialNetworkMapper, FeatureService featureService) {
        this.instagramRepository = instagramRepository;
        this.deviceInformationService = deviceInformationService;
        this.socialNetworkMapper = socialNetworkMapper;
        this.featureService = featureService;
    }

    public Page<SocialNetworkDTO> findByUserLoginAndDeviceId(String deviceId, Pageable pageable) {
        Optional<DeviceInformation> deviceInformation = featureService.findDeviceInformationWithPermission(deviceId,"/user/instagrams", "Instagram");
        Page<Instagram> instagrams = instagramRepository.findInstagramByDeviceId(deviceInformation.get().getId(), pageable);
        return instagrams.map(socialNetworkMapper::asSocialNetworkGetDataDTOForInstagram);
    }

    public Page<SocialNetworkDTO> findByUserLoginAndDeviceIdForAdmin(String login, String deviceId, Pageable pageable) {
        Optional<DeviceInformation> deviceInformation = deviceInformationService.findOneByUserLoginForAdmin(login,deviceId,ENTITY_NAME);
        Page<Instagram> instagrams = instagramRepository.findInstagramByDeviceId(deviceInformation.get().getId(), pageable);
        return  instagrams.map(socialNetworkMapper::asSocialNetworkGetDataDTOForInstagram);
    }

    public Instagram save(Instagram instagram) {
        log.debug("Request to save Instagram : {}", instagram);
        return instagramRepository.save(instagram);
    }

    public Optional<Instagram> findInstagramByContactAndDate(String contact, Long date) {
        return instagramRepository.findInstagramByContactAndDate(contact,date);
    }
}
