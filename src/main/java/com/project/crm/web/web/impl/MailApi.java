package com.project.crm.web.web.impl;

import com.project.crm.web.dto.ContactCreateDto;
import com.project.crm.web.web.api.IMailApi;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin("*")
public class MailApi implements IMailApi {
    @Autowired
    private JavaMailSender emailSender;
    private Object httpResponseBody;
    private HttpStatus httpStatus;

    @Override
    public ResponseEntity<?> SendMail(@ApiParam(required = true, value = "contact", name = "contact")
                                          @RequestBody String mail, BindingResult bindingResult) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setSubject("test");
        message.setText("test");
        emailSender.send(message);
        httpResponseBody = message;
        httpStatus = HttpStatus.OK;
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }
}
