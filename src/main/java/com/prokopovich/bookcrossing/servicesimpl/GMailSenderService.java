package com.prokopovich.bookcrossing.servicesimpl;

import com.prokopovich.bookcrossing.configs.GMailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class GMailSenderService {

    @Autowired
    private JavaMailSender sender;
    public void sendMessage(String to,String subject,String message){
        SimpleMailMessage simpleMailMessage =new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        sender.send(simpleMailMessage);
    }
}
