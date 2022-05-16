package com.tracer.app.service;

import com.tracer.app.domain.DeviceInformation;
import com.tracer.app.domain.FacebookMessenger;
import com.tracer.app.dto.SocialNetworkDTO;
import com.tracer.app.dto.mapper.SocialNetworkMapper;
import com.tracer.app.repository.FacebookMessengerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class FacebookMessengerService {
    private final Logger log = LoggerFactory.getLogger(FacebookMessengerService.class);

    private static final String ENTITY_NAME = "facebookMessengerService";

    private final FacebookMessengerRepository facebookMessengerRepository;
    private final DeviceInformationService deviceInformationService;
    private final SocialNetworkMapper socialNetworkMapper;
    private final FeatureService featureService;


    public FacebookMessengerService(FacebookMessengerRepository facebookMessengerRepository, DeviceInformationService deviceInformationService, SocialNetworkMapper socialNetworkMapper, FeatureService featureService) {
        this.facebookMessengerRepository = facebookMessengerRepository;
        this.deviceInformationService = deviceInformationService;
        this.socialNetworkMapper = socialNetworkMapper;
        this.featureService = featureService;
    }

    /**
     * Save a FacebookMessenger.
     *
     * @param facebookMessenger the entity to save.
     * @return the persisted entity.
     */
    public FacebookMessenger save(FacebookMessenger facebookMessenger) {
        log.debug("Request to save FacebookMessenger : {}", facebookMessenger);
        return facebookMessengerRepository.save(facebookMessenger);
    }

    public Page<SocialNetworkDTO> findByUserLoginAndDeviceId(String deviceId, Pageable pageable) {
        Optional<DeviceInformation> deviceInformation = featureService.findDeviceInformationWithPermission(deviceId,"/user/facebook-messengers", "FacebookMessengers");
        Page<FacebookMessenger> facebookMessengers = facebookMessengerRepository.findFacebookMessengerByDeviceId(deviceInformation.get().getId(), pageable);
        return facebookMessengers.map(socialNetworkMapper::asSocialNetworkGetDataDTOForFacebook);
    }

    public Page<SocialNetworkDTO> findByUserLoginAndDeviceIdForAdmin(String login, String deviceId, Pageable pageable) {
        Optional<DeviceInformation> deviceInformation = deviceInformationService.findOneByUserLoginForAdmin(login,deviceId,ENTITY_NAME);
        Page<FacebookMessenger> facebookMessengers = facebookMessengerRepository.findFacebookMessengerByDeviceId(deviceInformation.get().getId(), pageable);
        return  facebookMessengers.map(socialNetworkMapper::asSocialNetworkGetDataDTOForFacebook);
    }

    public Optional<FacebookMessenger> findFacebookMessengerByContactAndDate(String contact, Long date) {
        return facebookMessengerRepository.findFacebookMessengerByContactAndDate(contact,date);
    }
}
