package com.tracer.app.dto.mapper;

import com.tracer.app.domain.*;
import com.tracer.app.dto.SocialNetworkDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SocialNetworkMapper {

    SocialNetworkDTO asSocialNetworkGetDataDTOForFacebook(FacebookMessenger facebookMessenger);
    SocialNetworkDTO asSocialNetworkGetDataDTOForInstagram(Instagram instagram);
    SocialNetworkDTO asSocialNetworkGetDataDTOForSkype(Skype skype);
    SocialNetworkDTO asSocialNetworkGetDataDTOForTikTok(Tiktok tiktok);
    SocialNetworkDTO asSocialNetworkGetDataDTOForZalo(Zalo zalo);

    FacebookMessenger asFacebookMessenger(SocialNetworkDTO socialNetworkPushDataDTO);
    Instagram asInstagram(SocialNetworkDTO socialNetworkPushDataDTO);
    Skype asSkype(SocialNetworkDTO socialNetworkPushDataDTO);
    Tiktok asTiktok(SocialNetworkDTO socialNetworkPushDataDTO);
    Zalo asZalo(SocialNetworkDTO socialNetworkPushDataDTO);

}
