package com.project.crm.web.web.impl;

import com.project.crm.persistence.enumeration.Priorite;
import com.project.crm.persistence.enumeration.StatusProspect;
import com.project.crm.persistence.enumeration.TypeContact;
import com.project.crm.persistence.model.Contact;
import com.project.crm.persistence.model.Prospects;
import com.project.crm.service.IContactService;
import com.project.crm.service.IProspectsService;
import com.project.crm.web.common.ApiMessage;
import com.project.crm.web.common.utils.HttpErrorResponse;
import com.project.crm.web.common.utils.HttpMessageResponse;
import com.project.crm.web.dto.ContactCreateDto;
import com.project.crm.web.dto.ProspectCreateDto;
import com.project.crm.web.mapper.ContactMapper;
import com.project.crm.web.mapper.ProspectMapper;
import com.project.crm.web.web.api.IProspectApi;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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
public class ProspectApi implements IProspectApi {
    private Object httpResponseBody;

    private HttpStatus httpStatus;


    @Autowired
    private IProspectsService prospectsService;

    @Override
    public ResponseEntity<?> getProspects() {
        log.info("Web service getProject is invoked");
        try {
            List<Prospects> contacts = prospectsService.findAll();
            if(contacts.isEmpty()){
                httpResponseBody = contacts;;
            }else {
                httpResponseBody = contacts.stream().map(ProspectMapper.MAPPER::toProspectDto).collect(Collectors.toList());
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
    public ResponseEntity<?> getProspectById(@PathVariable("prospectId") Long prospectId) {
        log.info("Web service getProspectById is invoked");
        try {
            Optional<Prospects> prospect = prospectsService.findById(prospectId);
            if (prospect.isPresent()) {
                httpStatus = HttpStatus.OK;
                httpResponseBody = ProspectMapper.MAPPER.toProspectDto(prospect.get());
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
    public ResponseEntity<?> saveProspect(@ApiParam(required = true, value = "project", name = "project")
                                              @RequestBody ProspectCreateDto prospectCreateDto, BindingResult bindingResult ) {
        log.info("Web service saveProspect is invoked with args prospectCreateDto {}", prospectCreateDto);
        if (!bindingResult.hasFieldErrors()) {
            try {
                Optional<Prospects> prospect = prospectsService.findByEmail(prospectCreateDto.getEmail());
                if (!prospect.isEmpty()) {
                    httpStatus = HttpStatus.BAD_REQUEST;
                    httpResponseBody = new HttpErrorResponse(400, ApiMessage.CONTACT_ALREADY_EXIST);
                } else {
                    Prospects newProspect = ProspectMapper.MAPPER.toProspect(prospectCreateDto);
                    Prospects ProspectToBeSave =prospectsService.save(newProspect);
                    httpResponseBody = ProspectMapper.MAPPER.toProspectDto(ProspectToBeSave);
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
    public ResponseEntity<?> editProspect(@PathVariable("prospectId") Long prospectId, @RequestBody ProspectCreateDto prospectCreateDto , BindingResult bindingResult ) {
        log.info("Web service editProspect invoked with id {} and prospectCreateDto {}", prospectId, prospectCreateDto);
        if (!bindingResult.hasFieldErrors()) {
            try {
                Optional<Prospects> prospect = prospectsService.findById(prospectId);
                if (prospect.isPresent()) {
                    updateExistantProject(prospectCreateDto, prospect.get());
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
    public ResponseEntity<?> deleteProspect(@ApiParam(value = "ID of prospect that will be deleted", required = true, allowableValues = "range[1,infinity]") @PathVariable("prospectId") Long prospectId) {
        log.info("Web service deleteContact invoked with id {}", prospectId);
        try {
            Optional<Prospects> prospect = prospectsService.findById(prospectId);
            if (prospect.isPresent()) {
                prospectsService.delete(prospect.get());
                httpResponseBody = new HttpMessageResponse(ApiMessage.CONTACT_DELETED_SUCCESSFULLY);
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
    private void updateExistantProject(ProspectCreateDto prospectCreateDto, Prospects prospects) {
        prospects.setAdresse(prospectCreateDto.getAdresse());
        prospects.setNom(prospectCreateDto.getNom());
        prospects.setPrenom(prospectCreateDto.getPrenom());
        prospects.setEmail(prospectCreateDto.getEmail());
        prospects.setTelephone(prospectCreateDto.getTelephone());
        prospects.setNote(prospectCreateDto.getNote());
        prospects.setStatusProspect(StatusProspect.valueOf(prospectCreateDto.getStatusProspect()));
        prospectsService.update(prospects);
        httpResponseBody = ProspectMapper.MAPPER.toProspectDto(prospects);
        httpStatus = HttpStatus.OK;
    }

}
