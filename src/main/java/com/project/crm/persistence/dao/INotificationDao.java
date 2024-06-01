package com.project.crm.persistence.dao;


import com.project.crm.persistence.model.Contact;
import com.project.crm.persistence.model.Notification;
import com.project.crm.persistence.model.Tasks;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface INotificationDao extends IGenericDao<Notification, Long>{
    Optional<Notification> findByTaskId(Long taskId);
    @Query("SELECT t FROM Notification t WHERE :date BETWEEN t.dateDebut AND t.dateFin")
    List<Notification> findTasksByDate(@Param("date") LocalDate date);
}
