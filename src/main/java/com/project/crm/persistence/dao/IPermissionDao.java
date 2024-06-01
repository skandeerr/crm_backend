package com.project.crm.persistence.dao;

import com.project.crm.persistence.model.Permission;
import com.project.crm.persistence.model.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPermissionDao extends IGenericDao<Permission, Long>{
    boolean existsByRoles(Role role);
    Optional<Permission> findByName(String permission);


}
