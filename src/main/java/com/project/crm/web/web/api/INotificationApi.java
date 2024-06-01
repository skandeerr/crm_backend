package com.project.crm.web.web.api;

import com.project.crm.web.common.ApiMessage;
import com.project.crm.web.common.ApiStatus;
import com.project.crm.web.common.utils.HttpErrorResponse;
import com.project.crm.web.dto.NotificationDto;
import com.project.crm.web.dto.TaskCreateDto;
import com.project.crm.web.dto.TaskDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "/api/notification")
public interface INotificationApi {
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API_GET_TASKS_SUCCESS",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @ApiOperation(value = "get all notification", response = List.class )
    @GetMapping(value = "/DayNotification")
    ResponseEntity<?> getNotificationsOfDay();

    @io.swagger.annotations.ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.NOTIFICATION_UPDATED_SUCCESSFULLY, response = NotificationDto.class),
            @io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class)})
    @ApiOperation(value = "edit an existant notification", response = NotificationDto.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = {@AuthorizationScope(scope = "read", description = "")})})
    @PutMapping(value = "/{notificationId}")
    ResponseEntity<?> MakeNotificationToViewd(Long notificationId);
}
