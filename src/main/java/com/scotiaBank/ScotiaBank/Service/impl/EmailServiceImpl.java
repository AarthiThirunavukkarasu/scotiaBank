package com.scotiaBank.ScotiaBank.Service.impl;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.scotiaBank.ScotiaBank.Service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailServiceImpl implements EmailService{

    @Autowired
	    JavaMailSender javaMailSender;
    private static final Logger LOG = LoggerFactory.getLogger(EmailServiceImpl.class);
    public  void sendEmail(String to, String subject, String body) throws MessagingException {
    	 MimeMessage mimeMessage = javaMailSender.createMimeMessage();
         MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

         helper.setTo(to);
         helper.setSubject(subject);
         helper.setText(body, true);
         javaMailSender.send(mimeMessage);
    }
}
