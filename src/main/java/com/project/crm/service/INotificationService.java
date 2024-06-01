package com.project.crm.service;

import com.project.crm.persistence.model.Contact;
import com.project.crm.persistence.model.Notification;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional
public interface INotificationService extends IGenericService<Notification, Long>{
    Optional<Notification> findByTaskId(Long taskId);
    List<Notification> findTasksByDate();
}
