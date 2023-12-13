package com.prokopovich.bookcrossing.controllers;

import com.prokopovich.bookcrossing.servicesimpl.GMailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@Controller
@AllArgsConstructor
public class GmailController {
    private GMailSenderService senderService;

    @GetMapping("/send/{to}/{subject}/{message}")
    public ResponseEntity<String> sendMessage(@PathVariable(name = "to")String to,
                                              @PathVariable(name = "subject")String subject,
                                              @PathVariable(name = "message")String message){
        senderService.sendMessage(to, subject, message+" Server time: "+new Date());
        return ResponseEntity.ok().body("Message has been send !");
    }

    @GetMapping("/send-feedback")
    public String main(@RequestParam(name = "subject")String subject,
                       @RequestParam(name = "message")String message,
                       @AuthenticationPrincipal UserDetails userDetails) {
        if(userDetails==null){
            return "redirect:/login";
        }
        String sender =userDetails.getUsername();
        senderService.sendMessage("pro100mishqa63@gmail.com",subject,message+"\n Отправитель: "+sender);
        return "redirect:/main";
    }
}
