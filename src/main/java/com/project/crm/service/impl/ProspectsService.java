package com.project.crm.service.impl;

import com.project.crm.persistence.dao.IProspectsDao;
import com.project.crm.persistence.model.Prospects;
import com.project.crm.service.IProspectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ProspectsService extends GenericService<Prospects, Long> implements IProspectsService {
    @Autowired
    private IProspectsDao prospectsDao;
    @Override
    public Optional<Prospects> findByEmail(String email) {
        return prospectsDao.findByEmail(email);
    }
}
