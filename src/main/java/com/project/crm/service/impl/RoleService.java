package com.project.crm.service.impl;

import com.project.crm.persistence.dao.IRoleDao;
import com.project.crm.persistence.model.Contact;
import com.project.crm.persistence.model.Role;

import com.project.crm.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class RoleService extends GenericService<Role, Long> implements IRoleService {
    @Autowired
    private IRoleDao roleDao;
    @Override
    public Optional<Role> findByName(String name) {
        return roleDao.findByName(name);
    }

    @Override
    public List<Role> findByNameIn(Iterable<String> names) {
        return roleDao.findByNameIn(names);
    }
}
