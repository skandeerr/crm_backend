package com.project.crm.web.web.impl;

import com.project.crm.persistence.dao.UserRepository;
import com.project.crm.persistence.enumeration.Priorite;
import com.project.crm.persistence.enumeration.TypeContact;
import com.project.crm.persistence.model.Contact;
import com.project.crm.persistence.model.User;
import com.project.crm.service.IContactService;
import com.project.crm.web.common.ApiMessage;
import com.project.crm.web.common.utils.HttpErrorResponse;
import com.project.crm.web.common.utils.HttpMessageResponse;
import com.project.crm.web.dto.ContactCreateDto;
import com.project.crm.web.dto.UserDto;
import com.project.crm.web.mapper.ContactMapper;
import com.project.crm.web.web.api.IContactApi;
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
public class ContactApi implements IContactApi {
    private Object httpResponseBody;

    private HttpStatus httpStatus;


    @Autowired
    private IContactService contactService;
    @Autowired
    private UserRepository userService;

    @Override
    public ResponseEntity<?> getContacts() {
        log.info("Web service getContacts is invoked");
        try {
        List<Contact> contacts = contactService.findAll();
            if(contacts.isEmpty()){
                httpResponseBody = contacts;;
            }else {
                httpResponseBody = contacts.stream().map(ContactMapper.MAPPER::toContactDto).collect(Collectors.toList());
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
    public ResponseEntity<?> getContactById(@PathVariable("contactId") Long clientId) {
        log.info("Web service getContactById is invoked");
        try {
            Optional<Contact> contact = contactService.findById(clientId);
            if (contact.isPresent()) {
                httpStatus = HttpStatus.OK;
                httpResponseBody = ContactMapper.MAPPER.toContactDto(contact.get());
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
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long userId) {
        log.info("Web service getContactById is invoked");
        try {
            Optional<User> user = userService.findById(userId);
            if (user.isPresent()) {
                UserDto userDto = new UserDto();
                userDto.setUsername(user.get().getUsername());
                userDto.setEmail(user.get().getEmail());
                userDto.setFullName(user.get().getFullName());
                httpStatus = HttpStatus.OK;
                httpResponseBody = userDto;
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
    public ResponseEntity<?> saveContact(@ApiParam(required = true, value = "contact", name = "contact")
                                             @RequestBody ContactCreateDto contactCreateDto, BindingResult bindingResult) {
        log.info("Web service saveContact is invoked with args contactCreateDto {}", contactCreateDto);
        if (!bindingResult.hasFieldErrors()) {
            try {
                Optional<Contact> contact = contactService.findByEmail(contactCreateDto.getEmail());
                if (!contact.isEmpty()) {
                    httpStatus = HttpStatus.BAD_REQUEST;
                    httpResponseBody = new HttpErrorResponse(400, ApiMessage.CONTACT_ALREADY_EXIST);
                } else {
                    Contact newContact = ContactMapper.MAPPER.toContact(contactCreateDto);
                    Contact contactToBeSave =contactService.save(newContact);
                    httpResponseBody = ContactMapper.MAPPER.toContactDto(contactToBeSave);
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
    public ResponseEntity<?> editContact(@PathVariable("contactId") Long contactId, @RequestBody ContactCreateDto contactCreateDto , BindingResult bindingResult) {
        log.info("Web service editContact invoked with id {} and contactCreateDto {}", contactId, contactCreateDto);
        if (!bindingResult.hasFieldErrors()) {
            try {
                Optional<Contact> contact = contactService.findById(contactId);
                if (contact.isPresent()) {
                        updateExistantContact(contactCreateDto, contact.get());

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
    public ResponseEntity<?> deleteContact(@ApiParam(value = "ID of contact that will be deleted", required = true, allowableValues = "range[1,infinity]") @PathVariable("contactId") Long contactId) {
        log.info("Web service deleteContact invoked with id {}", contactId);
        try {
            Optional<Contact> contact = contactService.findById(contactId);
            if (contact.isPresent()) {

                contactService.delete(contact.get());
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

    private void updateExistantContact(ContactCreateDto contactCreateDto, Contact contact) {
        contact.setAdresse(contactCreateDto.getAdresse());
        contact.setNom(contactCreateDto.getNom());
        contact.setPrenom(contactCreateDto.getPrenom());
        contact.setEmail(contactCreateDto.getEmail());
        contact.setNote(contactCreateDto.getNote());
        contact.setTelephone(contactCreateDto.getTelephone());
        contact.setTypeContact(TypeContact.valueOf(contactCreateDto.getTypeContact()));
        contact.setPriorite(Priorite.valueOf(contactCreateDto.getPriorite()));
        contactService.update(contact);
        httpResponseBody = ContactMapper.MAPPER.toContactDto(contact);
        httpStatus = HttpStatus.OK;
    }

}
