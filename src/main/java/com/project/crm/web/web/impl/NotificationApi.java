package com.project.crm.web.web.impl;

import com.project.crm.persistence.model.Contact;
import com.project.crm.persistence.model.Notification;
import com.project.crm.service.IContactService;
import com.project.crm.service.INotificationService;
import com.project.crm.web.common.ApiMessage;
import com.project.crm.web.common.utils.HttpErrorResponse;
import com.project.crm.web.mapper.ContactMapper;
import com.project.crm.web.mapper.NotificationMapper;
import com.project.crm.web.web.api.INotificationApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.project.crm.web.common.ApiMessage.NOT_FOUND;
import static com.project.crm.web.common.ApiMessage.SERVER_ERROR_OCCURRED;

@Slf4j
@RestController
@CrossOrigin("*")
public class NotificationApi implements INotificationApi {
    private Object httpResponseBody;

    private HttpStatus httpStatus;


    @Autowired
    private INotificationService notificationService;
    @Override
    public ResponseEntity<?> getNotificationsOfDay() {
        log.info("Web service getNotificationsOfDay is invoked");
        try {
            List<Notification> notifications = notificationService.findTasksByDate();
            if(notifications.isEmpty()){
                httpResponseBody = notifications;
            }else {
                httpResponseBody = notifications.stream().filter(notification -> !notification.getIsViewd()).map(NotificationMapper.MAPPER::toNotificationDto).collect(Collectors.toList());
            }
            httpStatus = HttpStatus.OK;
        } catch (Exception ex) {
            httpResponseBody = new HttpErrorResponse(500, SERVER_ERROR_OCCURRED);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            log.error("ERROR - {}", ex);
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> MakeNotificationToViewd(@PathVariable("notificationId") Long notificationId) {
        log.info("Web service MakeNotificationToViewd is invoked with args notificationId {}", notificationId);

            try {
                Optional<Notification> notification = notificationService.findById(notificationId);
                if (notification.isEmpty()) {
                    httpStatus = HttpStatus.BAD_REQUEST;
                    httpResponseBody = new HttpErrorResponse(400, ApiMessage.CONTACT_ALREADY_EXIST);
                } else {
                    notification.get().setIsViewd(true);
                   notificationService.update(notification.get());
                    httpResponseBody = NotificationMapper.MAPPER.toNotificationDto(notification.get());
                    httpStatus = HttpStatus.OK;
                }
            } catch (Exception ex) {
                httpResponseBody = new HttpErrorResponse(500, SERVER_ERROR_OCCURRED);
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                log.error("ERROR - {}", ex);
            }

        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }
}
