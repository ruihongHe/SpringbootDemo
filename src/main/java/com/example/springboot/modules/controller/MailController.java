package com.example.springboot.modules.controller;

import com.example.springboot.common.util.Response;
import com.example.springboot.common.util.ResultCodeEnum;
import com.example.springboot.modules.service.MailService;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

/**
 * The type Mail controller.
 */
@Api("邮件控制类")
@RestController
@RequestMapping("/sysMail")
public class MailController {
    @Autowired
    private MailService mailService;

    /**
     * To send e mail response.
     *
     * @param mailAddress the mail address
     * @return the response
     * @throws MessagingException the messaging exception
     */
    @PostMapping("/toSend")
    public Response toSendEMail(@Param("mailAddress") String mailAddress) throws MessagingException {
        mailService.toSend(mailAddress);
        return Response.setResult(ResultCodeEnum.SUCCESS);
    }

}
