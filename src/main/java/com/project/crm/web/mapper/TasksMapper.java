package com.project.crm.web.mapper;

import com.project.crm.persistence.model.Prospects;
import com.project.crm.persistence.model.Tasks;
import com.project.crm.web.dto.ProspectCreateDto;
import com.project.crm.web.dto.ProspectDto;
import com.project.crm.web.dto.TaskCreateDto;
import com.project.crm.web.dto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {})
public interface TasksMapper {
    TasksMapper MAPPER = Mappers.getMapper(TasksMapper.class);

    TaskDto toTasksDto(Tasks tasks);
    Tasks toTasks(TaskCreateDto taskCreateDto);
}
