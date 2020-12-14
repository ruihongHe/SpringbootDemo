package com.example.springboot.modules.service;

import javax.mail.MessagingException;

public interface MailService {


    void toSend(String mailAddress) throws MessagingException;
}
