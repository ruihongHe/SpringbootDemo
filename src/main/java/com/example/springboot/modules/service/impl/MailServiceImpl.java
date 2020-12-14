package com.example.springboot.modules.service.impl;

import com.example.springboot.modules.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * The type Mail service.
 */
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String regFrom;
    @Override
    public void toSend(String mailAddress) throws MessagingException {
        //防止中文名字 base64加密以后 名字太长被截断 导致中文乱码问题
        System.getProperties().setProperty("mail.mime.splitlongparameters", "false");
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(regFrom);
        helper.setTo(mailAddress);
        mailSender.send(mimeMessage);
    }
}
