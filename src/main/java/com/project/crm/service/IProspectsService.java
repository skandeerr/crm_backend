package com.project.crm.service;


import com.project.crm.persistence.model.Prospects;

import java.util.Optional;

public interface IProspectsService extends IGenericService<Prospects, Long>{
    Optional<Prospects> findByEmail(String email);
}
