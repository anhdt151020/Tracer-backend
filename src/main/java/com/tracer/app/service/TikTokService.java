package com.tracer.app.service;

import com.tracer.app.domain.DeviceInformation;
import com.tracer.app.domain.Tiktok;
import com.tracer.app.dto.SocialNetworkDTO;
import com.tracer.app.dto.mapper.SocialNetworkMapper;
import com.tracer.app.repository.TiktokRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TikTokService {

    private final Logger log = LoggerFactory.getLogger(TikTokService.class);

    private static final String ENTITY_NAME = "tiktokService";

    private final TiktokRepository tiktokRepository;
    private final DeviceInformationService deviceInformationService;
    private final SocialNetworkMapper socialNetworkMapper;
    private final UserService userService;
    private final FeatureService featureService;

    public TikTokService(TiktokRepository tiktokRepository, DeviceInformationService deviceInformationService, SocialNetworkMapper socialNetworkMapper, UserService userService, FeatureService featureService) {
        this.tiktokRepository = tiktokRepository;
        this.deviceInformationService = deviceInformationService;
        this.socialNetworkMapper = socialNetworkMapper;
        this.userService = userService;
        this.featureService = featureService;
    }

    public Page<SocialNetworkDTO> findByUserLoginAndDeviceId(String deviceId, Pageable pageable) {
        Optional<DeviceInformation> deviceInformation = featureService.findDeviceInformationWithPermission(deviceId,"/user/tiktoks", "TikTok");
        Page<Tiktok> tiktoks = tiktokRepository.findTiktokByDeviceId(deviceInformation.get().getId(), pageable);
        return tiktoks.map(socialNetworkMapper::asSocialNetworkGetDataDTOForTikTok);
    }

    public Page<SocialNetworkDTO> findByUserLoginAndDeviceIdForAdmin(String login, String deviceId, Pageable pageable) {
        Optional<DeviceInformation> deviceInformation = deviceInformationService.findOneByUserLoginForAdmin(login,deviceId,ENTITY_NAME);
        Page<Tiktok> tiktoks = tiktokRepository.findTiktokByDeviceId(deviceInformation.get().getId(), pageable);
        return  tiktoks.map(socialNetworkMapper::asSocialNetworkGetDataDTOForTikTok);
    }

    public Tiktok save(Tiktok tiktok) {
        log.debug("Request to save TikTok : {}", tiktok);
        return tiktokRepository.save(tiktok);
    }

    public Optional<Tiktok> findTiktokByContactAndDate(String contact, Long date) {
        return tiktokRepository.findTiktokByContactAndDate(contact,date);
    }
}
