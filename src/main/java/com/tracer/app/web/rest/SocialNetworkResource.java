package com.tracer.app.web.rest;

import com.tracer.app.dto.SocialNetworkDTO;
import com.tracer.app.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SocialNetworkResource {

    private final Logger log = LoggerFactory.getLogger(SocialNetworkResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "SocialNetwork";


    private final InstagramService instagramService;
    private final FacebookMessengerService facebookMessengerService;
    private final ZaloService zaloService;
    private final TikTokService tikTokService;
    private final SkypeService skypeService;
    private final SocialNetworkService socialNetworkService;

    public SocialNetworkResource(InstagramService instagramService, FacebookMessengerService facebookMessengerService, ZaloService zaloService, TikTokService tikTokService, SkypeService skypeService, SocialNetworkService socialNetworkService) {
        this.instagramService = instagramService;
        this.facebookMessengerService = facebookMessengerService;
        this.zaloService = zaloService;
        this.tikTokService = tikTokService;
        this.skypeService = skypeService;
        this.socialNetworkService = socialNetworkService;
    }

    @PostMapping("/social-networks/list/{deviceId}")
    public void createSocialNetworks(
        @PathVariable("deviceId") String deviceId, @Valid @RequestBody List<SocialNetworkDTO> socialNetworkDTOS) throws URISyntaxException {
        log.debug("REST request to save CallPhones : {}",deviceId ,socialNetworkDTOS);
        socialNetworkService.saveSocialNetworkDatas(deviceId,socialNetworkDTOS);
        log.info("Success");
    }

    @PostMapping("/social-networks/{deviceId}")
    public void createSocialNetwork(
        @PathVariable("deviceId") String deviceId, @Valid @RequestBody SocialNetworkDTO socialNetworkDTO) throws URISyntaxException {
        log.debug("REST request to save CallPhones : {}",deviceId ,socialNetworkDTO);
        socialNetworkService.saveSocialNetworkData(deviceId,socialNetworkDTO);
        log.info("Success");
    }

    @GetMapping("/user/facebook-messengers/{deviceId}")
    public ResponseEntity<List<SocialNetworkDTO>> getAllFacebookMessengerByUserLoginAndDeviceId(
        @PathVariable String deviceId, Pageable pageable
    ) {
        Page<SocialNetworkDTO> page = facebookMessengerService.findByUserLoginAndDeviceId(deviceId,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/admin/facebook-messengers/{login}/{deviceId}")
    public ResponseEntity<List<SocialNetworkDTO>> getAllFacebookMessengerByUserLoginAndDeviceIdForAdmin(
        @PathVariable String login, @PathVariable String deviceId, Pageable pageable
    ) {
        Page<SocialNetworkDTO> page = facebookMessengerService.findByUserLoginAndDeviceIdForAdmin(login,deviceId,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/user/instagarms/{deviceId}")
    public ResponseEntity<List<SocialNetworkDTO>> getAllInstagramByUserLoginAndDeviceId(
        @PathVariable String deviceId, Pageable pageable
    ) {
        Page<SocialNetworkDTO> page = instagramService.findByUserLoginAndDeviceId(deviceId,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/admin/instagarms/{login}/{deviceId}")
    public ResponseEntity<List<SocialNetworkDTO>> getAllInstagramByUserLoginAndDeviceIdForAdmin(
        @PathVariable String login, @PathVariable String deviceId, Pageable pageable
    ) {
        Page<SocialNetworkDTO> page = instagramService.findByUserLoginAndDeviceIdForAdmin(login,deviceId,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/user/tiktoks/{deviceId}")
    public ResponseEntity<List<SocialNetworkDTO>> getAllTiktokByUserLoginAndDeviceId(
        @PathVariable String deviceId, Pageable pageable
    ) {
        Page<SocialNetworkDTO> page = tikTokService.findByUserLoginAndDeviceId(deviceId,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/admin/tiktoks/{login}/{deviceId}")
    public ResponseEntity<List<SocialNetworkDTO>> getAllTiktokByUserLoginAndDeviceIdForAdmin(
        @PathVariable String login, @PathVariable String deviceId, Pageable pageable
    ) {
        Page<SocialNetworkDTO> page = tikTokService.findByUserLoginAndDeviceIdForAdmin(login,deviceId,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/user/zalos/{deviceId}")
    public ResponseEntity<List<SocialNetworkDTO>> getAllZaloByUserLoginAndDeviceId(
        @PathVariable String deviceId, Pageable pageable
    ) {
        Page<SocialNetworkDTO> page = zaloService.findByUserLoginAndDeviceId(deviceId,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/admin/zalos/{login}/{deviceId}")
    public ResponseEntity<List<SocialNetworkDTO>> getAllZaloByUserLoginAndDeviceIdForAdmin(
        @PathVariable String login, @PathVariable String deviceId, Pageable pageable
    ) {
        Page<SocialNetworkDTO> page = zaloService.findByUserLoginAndDeviceIdForAdmin(login,deviceId,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/user/skypes/{deviceId}")
    public ResponseEntity<List<SocialNetworkDTO>> getAllSkypeByUserLoginAndDeviceId(
        @PathVariable String deviceId, Pageable pageable
    ) {
        Page<SocialNetworkDTO> page = skypeService.findByUserLoginAndDeviceId(deviceId,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/admin/skypes/{login}/{deviceId}")
    public ResponseEntity<List<SocialNetworkDTO>> getAllSkypeByUserLoginAndDeviceIdForAdmin(
        @PathVariable String login, @PathVariable String deviceId, Pageable pageable
    ) {
        Page<SocialNetworkDTO> page = skypeService.findByUserLoginAndDeviceIdForAdmin(login,deviceId,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
