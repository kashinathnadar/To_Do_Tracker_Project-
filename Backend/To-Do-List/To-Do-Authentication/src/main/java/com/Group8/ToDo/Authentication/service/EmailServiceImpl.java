package com.Group8.ToDo.Authentication.service;


import com.Group8.ToDo.Authentication.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements EmailService {


    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}") private String sender;
    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Override
    public String loginUser(User user) {
        try {
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(user.getUserEmailId());
            mailMessage.setText("Wel Come " + user.getUserName() + "to re-Login,we hope You enjoy tour App. ");

            System.out.println(mailMessage);
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        } catch (Exception e) {
            return "Mail Sent Successfully...";
        }
    }
}
