package com.project.crm.service.impl;


import com.project.crm.persistence.dao.ITasksDao;
import com.project.crm.persistence.model.Tasks;
import com.project.crm.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
public class TasksService  extends GenericService<Tasks, Long> implements ITaskService {
    @Autowired
    private ITasksDao tasksDao;
    @Override
    public Optional<Tasks> findByName(String name) {
        return tasksDao.findByName(name);
    }

    @Override
    public List<Tasks> getTasksForToday() {
        LocalDate today = LocalDate.now();
        return tasksDao.findTasksByDate(today);
    }
}
