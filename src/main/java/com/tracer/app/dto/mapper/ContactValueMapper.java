package com.tracer.app.dto.mapper;

import com.tracer.app.domain.Contact;
import com.tracer.app.dto.ContactDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactValueMapper {
    ContactDTO asContactDTO(Contact contact);

    Contact asContact(ContactDTO contactDTO);

    List<ContactDTO> asContactDTOs(List<Contact> contacts);

    List<Contact> asContacts(List<ContactDTO> contactDTOS);

}
