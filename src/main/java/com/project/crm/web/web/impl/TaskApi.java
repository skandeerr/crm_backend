package com.project.crm.web.web.impl;

import com.project.crm.persistence.enumeration.Priorite;
import com.project.crm.persistence.enumeration.StatusTask;
import com.project.crm.persistence.enumeration.TypeActivity;
import com.project.crm.persistence.enumeration.TypeContact;
import com.project.crm.persistence.model.Contact;
import com.project.crm.persistence.model.Notification;
import com.project.crm.persistence.model.Tasks;
import com.project.crm.service.IContactService;
import com.project.crm.service.INotificationService;
import com.project.crm.service.ITaskService;
import com.project.crm.web.common.ApiMessage;
import com.project.crm.web.common.utils.HttpErrorResponse;
import com.project.crm.web.common.utils.HttpMessageResponse;
import com.project.crm.web.dto.ContactCreateDto;
import com.project.crm.web.dto.TaskCreateDto;
import com.project.crm.web.mapper.ContactMapper;
import com.project.crm.web.mapper.TasksMapper;
import com.project.crm.web.web.api.ITasksApi;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.project.crm.web.common.ApiMessage.NOT_FOUND;
import static com.project.crm.web.common.ApiMessage.SERVER_ERROR_OCCURRED;
@Slf4j
@RestController
@CrossOrigin("*")
public class TaskApi implements ITasksApi {
    private Object httpResponseBody;

    private HttpStatus httpStatus;
    @Autowired
    private INotificationService notificationService;

    @Autowired
    private ITaskService taskService;
    @Override
    public ResponseEntity<?> getTasks() {
        log.info("Web service getTasks is invoked");
        try {
            List<Tasks> tasks = taskService.findAll();
            if(tasks.isEmpty()){
                httpResponseBody = tasks;;
            }else {
                httpResponseBody = tasks.stream().map(TasksMapper.MAPPER::toTasksDto).collect(Collectors.toList());
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
    public ResponseEntity<?> getTasksOfDay() {
        log.info("Web service getTasks is invoked");
        try {
            List<Tasks> tasks = taskService.getTasksForToday();
            if(tasks.isEmpty()){
                httpResponseBody = tasks;;
            }else {
                httpResponseBody = tasks.stream().filter(tasks1 -> !tasks1.getStatusTask().equals(StatusTask.FAIT)).map(TasksMapper.MAPPER::toTasksDto).collect(Collectors.toList());
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
    public ResponseEntity<?> getTaskById(@PathVariable("taskId") Long taskId) {
        log.info("Web service getTaskById is invoked");
        try {
            Optional<Tasks> task = taskService.findById(taskId);
            if (task.isPresent()) {
                httpStatus = HttpStatus.OK;
                httpResponseBody = TasksMapper.MAPPER.toTasksDto(task.get());
            } else {
                httpStatus = HttpStatus.NOT_FOUND;
                httpResponseBody = new HttpErrorResponse(404, NOT_FOUND);
            }
        } catch (Exception ex) {
            httpResponseBody = new HttpErrorResponse(500, SERVER_ERROR_OCCURRED);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            log.error("ERROR - {}", ex);
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> saveTask(@ApiParam(required = true, value = "task", name = "task")
                                          @RequestBody TaskCreateDto taskCreateDto, BindingResult bindingResult ) {
        log.info("Web service saveTask is invoked with args taskCreateDto {}", taskCreateDto);
        if (!bindingResult.hasFieldErrors()) {
            try {
                    Tasks newTasks = TasksMapper.MAPPER.toTasks(taskCreateDto);
                    Tasks taskToBeSave =taskService.save(newTasks);
                    Notification notification = new Notification();
                    notification.setDateDebut(taskToBeSave.getDateDebut());
                    notification.setDateFin(taskToBeSave.getDateFin());
                    notification.setNomTache(taskToBeSave.getName());
                    notification.setTaskId(taskToBeSave.getId());
                    notification.setIsViewd(false);
                    notificationService.save(notification);
                    httpResponseBody = TasksMapper.MAPPER.toTasksDto(taskToBeSave);
                    httpStatus = HttpStatus.OK;
            } catch (Exception ex) {
                httpResponseBody = new HttpErrorResponse(500, SERVER_ERROR_OCCURRED);
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                log.error("ERROR - {}", ex);
            }
        } else {
            httpResponseBody = new HttpErrorResponse(400, ApiMessage.REQUIRED_VALIDATION_FAILED);
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> editTask(@PathVariable("taskId") Long taskId, @RequestBody TaskCreateDto taskCreateDto , BindingResult bindingResult) {
        log.info("Web service editTask invoked with id {} and taskCreateDto {}", taskId, taskCreateDto);
        if (!bindingResult.hasFieldErrors()) {
            try {
                Optional<Tasks> task = taskService.findById(taskId);
                if (task.isPresent()) {
                    updateExistantTask(taskCreateDto, task.get());

                } else {
                    httpStatus = HttpStatus.NOT_FOUND;
                    httpResponseBody = new HttpErrorResponse(404, NOT_FOUND);
                }
            } catch (Exception ex) {
                httpResponseBody = new HttpErrorResponse(500, SERVER_ERROR_OCCURRED);
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                log.error("ERROR - {}", ex);
            }
        } else {
            httpResponseBody = new HttpErrorResponse(400, ApiMessage.REQUIRED_VALIDATION_FAILED);
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> deleteTask(@ApiParam(value = "ID of task that will be deleted", required = true, allowableValues = "range[1,infinity]") @PathVariable("taskId") Long taskId) {
        log.info("Web service deleteContact invoked with id {}", taskId);
        try {
            Optional<Tasks> task = taskService.findById(taskId);
            if (task.isPresent()) {

                taskService.delete(task.get());
                Optional<Notification> notification = notificationService.findByTaskId(taskId);
                if(notification.isPresent()){
                    notificationService.delete(notification.get());
                }
                httpResponseBody = new HttpMessageResponse(ApiMessage.TASK_DELETED_SUCCESSFULLY);
                httpStatus = HttpStatus.OK;
            } else {
                httpResponseBody = new HttpErrorResponse(404, ApiMessage.NOT_FOUND);
                httpStatus = HttpStatus.NOT_FOUND;
            }
        } catch (Exception ex) {
            httpResponseBody = new HttpErrorResponse(500, ApiMessage.ERROR_DELETE_CONTACT);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            log.error(ApiMessage.ERROR_LEVEL_MESSAGE, ex);
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    private void updateExistantTask(TaskCreateDto taskCreateDto, Tasks tasks) {
        tasks.setName(taskCreateDto.getName());
        tasks.setDescription(taskCreateDto.getDescription());
        tasks.setDateDebut(taskCreateDto.getDateDebut());
        tasks.setDateFin(taskCreateDto.getDateFin());
        tasks.setTypeActivity(TypeActivity.valueOf(taskCreateDto.getTypeActivity()));
        tasks.setStatusTask(StatusTask.valueOf(taskCreateDto.getStatusTask()));
        taskService.update(tasks);
        Optional<Notification> notification = notificationService.findByTaskId(tasks.getId());
        if(notification.isPresent()) {
            notification.get().setDateFin(tasks.getDateFin());
            notification.get().setDateDebut(tasks.getDateDebut());
            notification.get().setNomTache(tasks.getName());
            notificationService.update(notification.get());
        }
        httpResponseBody = TasksMapper.MAPPER.toTasksDto(tasks);
        httpStatus = HttpStatus.OK;
    }
}
