package com.tracer.app.web.rest;

import com.tracer.app.dto.ContactDTO;
import com.tracer.app.dto.SearchDTO;
import com.tracer.app.service.ContactService;
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
public class ContactResource {
    private final Logger log = LoggerFactory.getLogger(ContactResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactService contactService;

    public ContactResource(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/contacts/list/{deviceId}")
    public void createContacts(@PathVariable("deviceId") String deviceId, @RequestBody List<ContactDTO> contactDTOS) throws URISyntaxException {
        log.debug("REST request to save Contacts : {}", deviceId, contactDTOS);
        contactService.saveContacts(deviceId,contactDTOS);

        log.info("Success");
    }

    @GetMapping("/user/contacts/{deviceId}")
    public ResponseEntity<List<ContactDTO>> getAllContactByUserLoginAndDeviceId(
        @PathVariable String deviceId, Pageable pageable
    ) {
        Page<ContactDTO> page = contactService.findAllByUserLoginAndDeviceId(deviceId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/user/contacts/search/{deviceId}")
    public ResponseEntity<List<ContactDTO>> searchContacts(@PathVariable("deviceId") String deviceId,@Valid @RequestBody SearchDTO searchDTO, Pageable pageable) {
        searchDTO.setDeviceId(deviceId);
        Page<ContactDTO> page = contactService.findAllByFilter(searchDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/admin/contacts/{login}/{deviceId}")
    public ResponseEntity<List<ContactDTO>> getAllContactByUserLoginAndDeviceId(
        @PathVariable String login, @PathVariable String deviceId, Pageable pageable
    ) {
        Page<ContactDTO> page = contactService.findAllByUserLoginAndDeviceIdForAdmin(login,deviceId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
