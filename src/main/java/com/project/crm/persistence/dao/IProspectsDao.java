package com.project.crm.persistence.dao;

import com.project.crm.persistence.model.Prospects;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProspectsDao extends IGenericDao<Prospects, Long>{
    Optional<Prospects> findByEmail(String email);
}
