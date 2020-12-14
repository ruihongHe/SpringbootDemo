package com.example.springboot.modules.service;

import javax.mail.MessagingException;

/**
 * The interface Mail service.
 */
public interface MailService {


    /**
     * To send.
     *
     * @param mailAddress the mail address
     * @throws MessagingException the messaging exception
     */
    void toSend(String mailAddress) throws MessagingException;
}
