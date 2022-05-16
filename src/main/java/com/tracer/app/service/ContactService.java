package com.tracer.app.service;

import com.tracer.app.domain.Contact;
import com.tracer.app.domain.DeviceInformation;
import com.tracer.app.dto.ContactDTO;
import com.tracer.app.dto.SearchDTO;
import com.tracer.app.dto.mapper.ContactValueMapper;
import com.tracer.app.repository.ContactRepository;
import com.tracer.app.repository.specification.ContactSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContactService {
    private final Logger log = LoggerFactory.getLogger(ContactService.class);

    private static final String ENTITY_NAME = "contactService";

    private final ContactRepository contactRepository;
    private final DeviceInformationService deviceInformationService;
    private final ContactValueMapper contactValueMapper;
    private final UserService userService;
    private final FeatureService featureService;

    public ContactService(ContactRepository contactRepository, DeviceInformationService deviceInformationService, ContactValueMapper contactValueMapper, UserService userService, FeatureService featureService) {
        this.contactRepository = contactRepository;
        this.deviceInformationService = deviceInformationService;
        this.contactValueMapper = contactValueMapper;
        this.userService = userService;
        this.featureService = featureService;
    }

    public void saveContacts(String deviceId, List<ContactDTO> contactDTOS) {
        log.debug("Request to save Contacts : {}",deviceId ,contactDTOS);

        DeviceInformation deviceInformation = deviceInformationService.getByUserLoginAndDeviceId(deviceId);

        List<Contact> contacts = contactValueMapper.asContacts(contactDTOS);

        List<Contact> existContacts = contactRepository.findAllByDeviceId(deviceInformation.getId());
        contacts.removeAll(existContacts);

        if(!contacts.isEmpty()){
            contacts = contacts.stream().map(c -> {
                c.setDevice(deviceInformation);
                return c;
            }).collect(Collectors.toList());

            contactRepository.saveAll(contacts);
        }
    }

    public Page<ContactDTO> findAllByUserLoginAndDeviceId(String deviceId, Pageable pageable) {
        Optional<DeviceInformation> deviceInformation = featureService.findDeviceInformationWithPermission(deviceId,"/user/contacts","Contacts");
        Page<Contact> contacts = contactRepository.findByDeviceId(deviceInformation.get().getId(), pageable);
        return contacts.map(contactValueMapper::asContactDTO);
    }

    public Page<ContactDTO> findAllByUserLoginAndDeviceIdForAdmin(String login, String deviceId, Pageable pageable) {
        Optional<DeviceInformation> deviceInformation = deviceInformationService.findOneByUserLoginForAdmin(login,deviceId,ENTITY_NAME);
        Page<Contact> contacts = contactRepository.findByDeviceId(deviceInformation.get().getId(), pageable);
        return contacts.map(contactValueMapper::asContactDTO);
    }

    public Page<ContactDTO> findAllByFilter(SearchDTO searchDTO, Pageable pageable) {
        userService.checkUserAccessPermission("/user/contacts","Contacts");
        deviceInformationService.findByDeviceIdThrowException(searchDTO.getDeviceId());
        Page<Contact> contacts = contactRepository.findAll(ContactSpecification.filterSearch(searchDTO), pageable);
        return contacts.map(contactValueMapper::asContactDTO);
    }
}
