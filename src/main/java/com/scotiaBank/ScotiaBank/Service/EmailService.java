package com.scotiaBank.ScotiaBank.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

@Service
public interface EmailService {

   
    public  void sendEmail(String to, String subject, String body) throws MessagingException ;
}
