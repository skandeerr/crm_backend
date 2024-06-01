package com.project.crm.service.impl;

import com.project.crm.persistence.dao.IContactDao;
import com.project.crm.persistence.model.Contact;
import com.project.crm.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ContactService extends GenericService<Contact, Long> implements IContactService {
    @Autowired
    private IContactDao contactDao;
    @Override
    public Optional<Contact> findByEmail(String email) {
        return contactDao.findByEmail(email);
    }
}
