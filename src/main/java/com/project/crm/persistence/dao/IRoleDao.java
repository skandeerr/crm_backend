package com.project.crm.persistence.dao;

import com.project.crm.persistence.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleDao extends IGenericDao<Role, Long>{
    Optional<Role> findByName(String role);
    List<Role> findByNameIn(Iterable<String> names);


}
