package com.prokopovich.bookcrossing.servicesimpl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

    public void sendActivationHTML(String to, String subject, String htmlContent) {
        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            sender.send(message);
            System.out.println("Сообщение отправлено успешно.");
        } catch (MessagingException | MailException e) {
            System.out.println("Ошибка при отправке сообщения: " + e.getMessage());
        }
    }
}

