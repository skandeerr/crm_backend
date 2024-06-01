package com.project.crm.web.web.impl;

import com.project.crm.persistence.enumeration.StatusTask;
import com.project.crm.persistence.enumeration.TypeActivity;
import com.project.crm.persistence.model.Notification;
import com.project.crm.persistence.model.Role;
import com.project.crm.persistence.model.Tasks;
import com.project.crm.service.IRoleService;
import com.project.crm.web.common.ApiMessage;
import com.project.crm.web.common.utils.HttpErrorResponse;
import com.project.crm.web.common.utils.HttpMessageResponse;
import com.project.crm.web.dto.RoleCreateDto;
import com.project.crm.web.dto.TaskCreateDto;
import com.project.crm.web.mapper.RoleMapper;
import com.project.crm.web.mapper.TasksMapper;
import com.project.crm.web.web.api.IRoleApi;
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
public class RoleApi implements IRoleApi {
    private Object httpResponseBody;

    private HttpStatus httpStatus;
    @Autowired
    private IRoleService roleService;
    @Override
    public ResponseEntity<?> getRoles() {
        log.info("Web service getTasks is invoked");
        try {
            List<Role> roles = roleService.findAll();
            if(roles.isEmpty()){
                httpResponseBody = roles;;
            }else {
                httpResponseBody = roles.stream().map(RoleMapper.MAPPER::toRoleDto).collect(Collectors.toList());
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
    public ResponseEntity<?> getRoleById(@PathVariable("roleId") Long roleId) {
        log.info("Web service getTaskById is invoked");
        try {
            Optional<Role> role = roleService.findById(roleId);
            if (role.isPresent()) {
                httpStatus = HttpStatus.OK;
                httpResponseBody = RoleMapper.MAPPER.toRoleDto(role.get());
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
    public ResponseEntity<?> saveRole(@ApiParam(required = true, value = "role", name = "role")
                                          @RequestBody RoleCreateDto roleCreateDto, BindingResult bindingResult) {
        log.info("Web service saveRole is invoked with args roleCreateDto {}", roleCreateDto);
        if (!bindingResult.hasFieldErrors()) {
            try {
                Optional<Role> role = roleService.findByName(roleCreateDto.getName());
                if (!role.isEmpty()) {
                    httpStatus = HttpStatus.BAD_REQUEST;
                    httpResponseBody = new HttpErrorResponse(400, ApiMessage.TASK_ALREADY_EXIST);
                } else {
                    Role newTasks = RoleMapper.MAPPER.toRole(roleCreateDto);
                    Role taskToBeSave =roleService.save(newTasks);
                    httpResponseBody = RoleMapper.MAPPER.toRoleDto(taskToBeSave);
                    httpStatus = HttpStatus.OK;
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
    public ResponseEntity<?> editRole(@PathVariable("roleId") Long roleId, @RequestBody RoleCreateDto roleCreateDto , BindingResult bindingResult) {
        log.info("Web service editRole invoked with id {} and roleCreateDto {}", roleId, roleCreateDto);
        if (!bindingResult.hasFieldErrors()) {
            try {
                Optional<Role> role = roleService.findById(roleId);
                if (role.isPresent()) {
                    role.get().setName(roleCreateDto.getName());
                    roleService.update(role.get());
                    httpResponseBody = RoleMapper.MAPPER.toRoleDto(role.get());
                    httpStatus = HttpStatus.OK;

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
    public ResponseEntity<?> deleteRole(@ApiParam(value = "ID of task that will be deleted", required = true, allowableValues = "range[1,infinity]") @PathVariable("roleId") Long roleId) {
        log.info("Web service deleteRole invoked with id {}", roleId);
        try {
            Optional<Role> role = roleService.findById(roleId);
            if (role.isPresent()) {
                roleService.delete(role.get());
                httpResponseBody = new HttpMessageResponse(ApiMessage.ROLE_DELETED_SUCCESSFULLY);
                httpStatus = HttpStatus.OK;
            } else {
                httpResponseBody = new HttpErrorResponse(404, ApiMessage.NOT_FOUND);
                httpStatus = HttpStatus.NOT_FOUND;
            }
        } catch (Exception ex) {
            httpResponseBody = new HttpErrorResponse(500, ApiMessage.ERROR_LEVEL_MESSAGE);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            log.error(ApiMessage.ERROR_LEVEL_MESSAGE, ex);
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }
}
