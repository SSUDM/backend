package com.DM.DeveloperMatching.controller;

import com.DM.DeveloperMatching.dto.Email.EmailRequest;
import com.DM.DeveloperMatching.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class EmailController {
    private final MailService mailService;
    @PostMapping("/send-email")
    public ResponseEntity<String> sendEMail(@RequestBody EmailRequest emailRequest) {
        int authNumber = mailService.sendMail(emailRequest.getEmail());
        String number = "" + authNumber;

        return ResponseEntity.ok().body(number);
    }
}
