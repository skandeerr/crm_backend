package com.project.crm.service.impl;

import com.project.crm.persistence.dao.INotificationDao;
import com.project.crm.persistence.model.Contact;
import com.project.crm.persistence.model.Notification;
import com.project.crm.service.IContactService;
import com.project.crm.service.INotificationService;
import com.project.crm.web.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
public class NotificationService extends GenericService<Notification, Long> implements INotificationService {
    @Autowired
    INotificationDao notificationDao;
    @Override
    public Optional<Notification> findByTaskId(Long taskId) {
        return notificationDao.findByTaskId(taskId);
    }

    @Override
    public List<Notification> findTasksByDate() {
        LocalDate today = LocalDate.now();
        return notificationDao.findTasksByDate(today);
    }
}
