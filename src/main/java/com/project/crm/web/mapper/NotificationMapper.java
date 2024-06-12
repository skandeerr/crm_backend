package com.project.crm.web.mapper;

import com.project.crm.persistence.model.Notification;
import com.project.crm.persistence.model.Prospects;
import com.project.crm.web.dto.NotificationDto;
import com.project.crm.web.dto.ProspectCreateDto;
import com.project.crm.web.dto.ProspectDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring", uses = {})
public interface NotificationMapper {
    NotificationMapper MAPPER = Mappers.getMapper(NotificationMapper.class);

/*
    NotificationDto toNotificationDto(Notification notification);
*/

    default NotificationDto toNotificationDto(Notification notification){
       return NotificationDto.builder().NomTache(notification.getNomTache()).isViewd(notification.getIsViewd()).dateDebut(notification.getDateDebut())
                .dateFin(notification.getDateFin()).taskId(notification.getTaskId()).id(notification.getId()).build();
    }
}
