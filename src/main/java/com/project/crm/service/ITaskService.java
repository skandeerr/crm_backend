package com.project.crm.service;

import com.project.crm.persistence.model.Tasks;

import java.util.List;
import java.util.Optional;

public interface ITaskService extends IGenericService<Tasks, Long>{
    Optional<Tasks> findByName(String name);
    List<Tasks> getTasksForToday();

}
