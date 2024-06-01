package com.project.crm.service.impl;

import com.project.crm.persistence.dao.IPermissionDao;
import com.project.crm.persistence.model.Permission;
import com.project.crm.persistence.model.Role;
import com.project.crm.service.IPermissionService;
import com.project.crm.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class PermissionService extends GenericService<Permission, Long> implements IPermissionService {
    @Autowired
    private IPermissionDao permissionDao;
    @Override
    public Optional<Permission> findByName(String name) {
        return permissionDao.findByName(name);
    }

    @Override
    public boolean existsByRoles(Role role) {
        return permissionDao.existsByRoles(role);
    }
}
