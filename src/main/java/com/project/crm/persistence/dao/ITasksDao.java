package com.project.crm.persistence.dao;

import com.project.crm.persistence.model.Tasks;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ITasksDao extends IGenericDao<Tasks, Long>{
    Optional<Tasks> findByName(String name);
    @Query("SELECT t FROM Tasks t WHERE :date BETWEEN t.dateDebut AND t.dateFin")
    List<Tasks> findTasksByDate(@Param("date") LocalDate date);

}
