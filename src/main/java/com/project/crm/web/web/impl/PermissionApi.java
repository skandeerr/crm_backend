package com.project.crm.web.web.impl;

import com.project.crm.persistence.model.Permission;
import com.project.crm.persistence.model.Role;
import com.project.crm.service.IPermissionService;
import com.project.crm.service.IRoleService;
import com.project.crm.web.common.ApiMessage;
import com.project.crm.web.common.utils.HttpErrorResponse;
import com.project.crm.web.common.utils.HttpMessageResponse;
import com.project.crm.web.dto.PermissionCreateDto;
import com.project.crm.web.dto.RoleCreateDto;
import com.project.crm.web.mapper.PermissionMapper;
import com.project.crm.web.mapper.RoleMapper;
import com.project.crm.web.web.api.IPermissionApi;
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
public class PermissionApi implements IPermissionApi {
    private Object httpResponseBody;

    private HttpStatus httpStatus;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionService permissionService;
    @Override
    public ResponseEntity<?> getPermissions() {
        log.info("Web service getTasks is invoked");
        try {
            List<Permission> permissions = permissionService.findAll();
            if(permissions.isEmpty()){
                httpResponseBody = permissions;;
            }else {
                httpResponseBody = permissions.stream().map(PermissionMapper.MAPPER::toPermissionDto).collect(Collectors.toList());
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
    public ResponseEntity<?> getPermissionById(@PathVariable("permissionId") Long permissionId) {
        log.info("Web service getTaskById is invoked");
        try {
            Optional<Permission> permission = permissionService.findById(permissionId);
            if (permission.isPresent()) {
                httpStatus = HttpStatus.OK;
                httpResponseBody = PermissionMapper.MAPPER.toPermissionDto(permission.get());
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
    public ResponseEntity<?> savePermission(@ApiParam(required = true, value = "permission", name = "permission")
                                                @RequestBody PermissionCreateDto permissionCreateDto, BindingResult bindingResult) {
        log.info("Web service savePermission is invoked with args permissionCreateDto {}", permissionCreateDto);
        if (!bindingResult.hasFieldErrors()) {
            try {
                Optional<Permission> permission = permissionService.findByName(permissionCreateDto.getName());
                if (!permission.isEmpty()) {
                    httpStatus = HttpStatus.BAD_REQUEST;
                    httpResponseBody = new HttpErrorResponse(400, ApiMessage.TASK_ALREADY_EXIST);
                } else {
                    Permission newPermission = new Permission();
                    newPermission.setName(permissionCreateDto.getName());
                    newPermission.setRoles(roleService.findByNameIn(permissionCreateDto.getRoles()));
                    Permission permoissionToBeSave =permissionService.save(newPermission);
                    httpResponseBody = PermissionMapper.MAPPER.toPermissionDto(permoissionToBeSave);
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
    public ResponseEntity<?> editPermission(@PathVariable("permissionId") Long permissionId, @RequestBody PermissionCreateDto permissionCreateDto , BindingResult bindingResult) {
        log.info("Web service editPermission invoked with id {} and permissionCreateDto {}", permissionId, permissionCreateDto);
        if (!bindingResult.hasFieldErrors()) {
            try {
                Optional<Permission> permission = permissionService.findById(permissionId);
                if (permission.isPresent()) {
                    permission.get().setName(permissionCreateDto.getName());
                    permission.get().setRoles(roleService.findByNameIn(permissionCreateDto.getRoles()));
                    permissionService.update(permission.get());
                    httpResponseBody = PermissionMapper.MAPPER.toPermissionDto(permission.get());
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
    public ResponseEntity<?> deletePermission(@ApiParam(value = "ID of task that will be deleted", required = true, allowableValues = "range[1,infinity]") @PathVariable("permissionId") Long permissionId) {
        log.info("Web service deletePermission invoked with id {}", permissionId);
        try {
            Optional<Permission> permission = permissionService.findById(permissionId);
            if (permission.isPresent()) {
                permissionService.delete(permission.get());
                httpResponseBody = new HttpMessageResponse(ApiMessage.PERMISSION_DELETED_SUCCESSFULLY);
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
