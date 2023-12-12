package com.prokopovich.bookcrossing.controllers;

import com.prokopovich.bookcrossing.servicesimpl.GMailSenderService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/mail")
@AllArgsConstructor
public class GmailRestController {
    private GMailSenderService senderService;

    @GetMapping("/send/{to}/{subject}/{message}")
    public ResponseEntity<String> sendMessage(@PathVariable(name = "to")String to,
                                              @PathVariable(name = "subject")String subject,
                                              @PathVariable(name = "message")String message){
        senderService.sendMessage(to, subject, message+" Server time: "+new Date());
        return ResponseEntity.ok().body("Message has been send !");
    }
}
