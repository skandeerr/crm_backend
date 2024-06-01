package com.project.crm.service;

import com.project.crm.persistence.model.Permission;
import com.project.crm.persistence.model.Role;

import java.util.Optional;

public interface IPermissionService extends IGenericService<Permission, Long>{
    Optional<Permission> findByName(String name);
    boolean existsByRoles(Role role);
}
