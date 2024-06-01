package com.project.crm.service;

import com.project.crm.persistence.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService extends IGenericService<Role, Long>{
    Optional<Role> findByName(String name);
    List<Role> findByNameIn(Iterable<String> names);

}
