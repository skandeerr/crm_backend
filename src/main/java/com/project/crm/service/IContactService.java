package com.project.crm.service;

import com.project.crm.persistence.model.Contact;

import java.util.Optional;

public interface IContactService extends IGenericService<Contact, Long>{
    Optional<Contact> findByEmail(String email);
}
