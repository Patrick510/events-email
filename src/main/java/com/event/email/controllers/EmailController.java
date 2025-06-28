package com.event.email.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.event.email.dtos.EmailDto;
import com.event.email.models.EmailModel;
import com.event.email.services.EmailService;

import java.util.List;

@RestController
@RequestMapping("/send-email")
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping
    public ResponseEntity<EmailModel> sendingEmail(@RequestBody @Valid EmailDto emailDto) {
        var emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDto, emailModel);
        return new ResponseEntity<>(emailService.sendEmail(emailModel), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmailModel>> getAllEmails() {
        List<EmailModel> emailsList = emailService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(emailsList);
    }
}
