package com.edu.nju.kanbanboard.service.impl;

import com.edu.nju.kanbanboard.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;


@Service
public class MailServcieImpl implements MailService {
    //@Autowired
    //private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public boolean sendInviteMail(Long ownerId, String targetEmail) {
        //MimeMessage mimeMailMessage = mailSender.createMimeMessage();
        return false;
    }
}
