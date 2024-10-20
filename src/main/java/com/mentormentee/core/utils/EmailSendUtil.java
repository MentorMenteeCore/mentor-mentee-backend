package com.mentormentee.core.utils;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class EmailSendUtil {
    @Value("${mail.host}")
    private String host;

    private final MailSender mailSender;
    public void sendEmail(String to, String title, String body){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(host);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(body);
        mailSender.send(simpleMailMessage);

    }


    
}