package com.project.crm.web.mapper;

import com.project.crm.persistence.enumeration.TypeContact;
import com.project.crm.persistence.model.Contact;
import com.project.crm.web.dto.ContactCreateDto;
import com.project.crm.web.dto.ContactDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {})
public interface ContactMapper {
    ContactMapper MAPPER = Mappers.getMapper(ContactMapper.class);

//    ContactDto toContactDto(Contact bank);
    Contact toContact(ContactCreateDto contactCreateDto);

    default ContactDto toContactDto(Contact contact) {
      return ContactDto.builder()
              .id(contact.getId())
              .nom(contact.getNom())
              .prenom(contact.getPrenom())
              .email(contact.getEmail())
              .adresse(contact.getAdresse())
              .telephone(contact.getTelephone())
              .typeContact(contact.getTypeContact().toString())
              .priorite(contact.getPriorite().toString())
              .build();
    }

}
