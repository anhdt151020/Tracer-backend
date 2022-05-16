package com.tracer.app.service;

import com.tracer.app.domain.*;
import com.tracer.app.domain.enumeration.StatusType;
import com.tracer.app.dto.SocialNetworkDTO;
import com.tracer.app.dto.mapper.SocialNetworkMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SocialNetworkService {
    private final Logger log = LoggerFactory.getLogger(SocialNetworkService.class);

    private final DeviceInformationService deviceInformationService;
    private final FacebookMessengerService facebookMessengerService;
    private final InstagramService instagramService;
    private final SkypeService skypeService;
    private final TikTokService tikTokService;
    private final ZaloService zaloService;
    private final SocialNetworkMapper socialNetworkMapper;

    public SocialNetworkService(DeviceInformationService deviceInformationService,
                                FacebookMessengerService facebookMessengerService,
                                InstagramService instagramService,
                                SkypeService skypeService,
                                TikTokService tikTokService,
                                ZaloService zaloService,
                                SocialNetworkMapper socialNetworkMapper) {
        this.deviceInformationService = deviceInformationService;
        this.facebookMessengerService = facebookMessengerService;
        this.instagramService = instagramService;
        this.skypeService = skypeService;
        this.tikTokService = tikTokService;
        this.zaloService = zaloService;
        this.socialNetworkMapper = socialNetworkMapper;
    }

    public void saveSocialNetworkDatas(String deviceId, List<SocialNetworkDTO> socialNetworkDTOS) {
        if(!socialNetworkDTOS.isEmpty()) {
            log.info("push data for social network");
            DeviceInformation deviceInformation = deviceInformationService.getByUserLoginAndDeviceId(deviceId);
            for (SocialNetworkDTO socialNetworkDTO : socialNetworkDTOS) {
                switch (socialNetworkDTO.getSocialNetworkEnum()) {
                    case FACEBOOK:
                        FacebookMessenger facebookMessenger = socialNetworkMapper.asFacebookMessenger(socialNetworkDTO);
                        Optional<FacebookMessenger> existFacebook = facebookMessengerService.findFacebookMessengerByContactAndDate(facebookMessenger.getContact(),facebookMessenger.getDate());
                        if (existFacebook.isPresent()){
                            break;
                        }
                        facebookMessenger.setDevice(deviceInformation);
                        facebookMessenger.setStatus(StatusType.OPEN);
                        facebookMessengerService.save(facebookMessenger);
                        break;
                    case INSTAGRAM:
                        Instagram instagram = socialNetworkMapper.asInstagram(socialNetworkDTO);
                        Optional<Instagram> existInstagram = instagramService.findInstagramByContactAndDate(instagram.getContact(),instagram.getDate());
                        if (existInstagram.isPresent()){
                            break;
                        }
                        instagram.setDevice(deviceInformation);
                        instagram.setStatus(StatusType.OPEN);
                        instagramService.save(instagram);
                        break;
                    case SKYPE:
                        Skype skype = socialNetworkMapper.asSkype(socialNetworkDTO);
                        Optional<Skype> existSkype = skypeService.findSkypeByContactAndDate(skype.getContact(),skype.getDate());
                        if (existSkype.isPresent()){
                            break;
                        }
                        skype.setDevice(deviceInformation);
                        skype.setStatus(StatusType.OPEN);
                        skypeService.save(skype);
                        break;
                    case TIKTOK:
                        Tiktok tiktok = socialNetworkMapper.asTiktok(socialNetworkDTO);
                        Optional<Tiktok> existTiktok = tikTokService.findTiktokByContactAndDate(tiktok.getContact(),tiktok.getDate());
                        if (existTiktok.isPresent()){
                            break;
                        }
                        tiktok.setDevice(deviceInformation);
                        tiktok.setStatus(StatusType.OPEN);
                        tikTokService.save(tiktok);
                        break;
                    case ZALO:
                        Zalo zalo = socialNetworkMapper.asZalo(socialNetworkDTO);
                        Optional<Zalo> existZalo = zaloService.findZaloByContactAndDate(zalo.getContact(),zalo.getDate());
                        if (existZalo.isPresent()){
                            break;
                        }
                        zalo.setDevice(deviceInformation);
                        zalo.setStatus(StatusType.OPEN);
                        zaloService.save(zalo);
                        break;
                }
            }
        }
    }

    public void saveSocialNetworkData(String deviceId, SocialNetworkDTO socialNetworkDTO) {
        DeviceInformation deviceInformation = deviceInformationService.getByUserLoginAndDeviceId(deviceId);
        switch (socialNetworkDTO.getSocialNetworkEnum()) {
            case FACEBOOK:
                FacebookMessenger facebookMessenger = socialNetworkMapper.asFacebookMessenger(socialNetworkDTO);
                Optional<FacebookMessenger> existFacebook = facebookMessengerService.findFacebookMessengerByContactAndDate(facebookMessenger.getContact(),facebookMessenger.getDate());
                if (existFacebook.isPresent()){
                    break;
                }
                facebookMessenger.setDevice(deviceInformation);
                facebookMessenger.setStatus(StatusType.OPEN);
                facebookMessengerService.save(facebookMessenger);
                break;
            case INSTAGRAM:
                Instagram instagram = socialNetworkMapper.asInstagram(socialNetworkDTO);
                Optional<Instagram> existInstagram = instagramService.findInstagramByContactAndDate(instagram.getContact(),instagram.getDate());
                if (existInstagram.isPresent()){
                    break;
                }
                instagram.setDevice(deviceInformation);
                instagram.setStatus(StatusType.OPEN);
                instagramService.save(instagram);
                break;
            case SKYPE:
                Skype skype = socialNetworkMapper.asSkype(socialNetworkDTO);
                Optional<Skype> existSkype = skypeService.findSkypeByContactAndDate(skype.getContact(),skype.getDate());
                if (existSkype.isPresent()){
                    break;
                }
                skype.setDevice(deviceInformation);
                skype.setStatus(StatusType.OPEN);
                skypeService.save(skype);
                break;
            case TIKTOK:
                Tiktok tiktok = socialNetworkMapper.asTiktok(socialNetworkDTO);
                Optional<Tiktok> existTiktok = tikTokService.findTiktokByContactAndDate(tiktok.getContact(),tiktok.getDate());
                if (existTiktok.isPresent()){
                    break;
                }
                tiktok.setDevice(deviceInformation);
                tiktok.setStatus(StatusType.OPEN);
                tikTokService.save(tiktok);
                break;
            case ZALO:
                Zalo zalo = socialNetworkMapper.asZalo(socialNetworkDTO);
                Optional<Zalo> existZalo = zaloService.findZaloByContactAndDate(zalo.getContact(),zalo.getDate());
                if (existZalo.isPresent()){
                    break;
                }
                zalo.setDevice(deviceInformation);
                zalo.setStatus(StatusType.OPEN);
                zaloService.save(zalo);
                break;
        }
    }
}
