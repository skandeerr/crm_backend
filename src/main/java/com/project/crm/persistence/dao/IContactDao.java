package com.project.crm.persistence.dao;

import com.project.crm.persistence.model.Contact;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IContactDao extends IGenericDao<Contact, Long>{
    Optional<Contact> findByEmail(String email);
}
