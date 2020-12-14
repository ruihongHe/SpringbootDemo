package com.example.springboot.modules.controller;

import com.example.springboot.common.util.Response;
import com.example.springboot.common.util.ResultCodeEnum;
import com.example.springboot.modules.service.MailService;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Api("邮件控制类")
@RestController
@RequestMapping("/sysMail")
public class MailController {
    @Autowired
    private MailService mailService;

    @RequestMapping("/toSend")
    public Response toSendEMail(@Param("mailAddress") String mailAddress){
        mailService.toSend(mailAddress);
        return Response.setResult(ResultCodeEnum.SUCCESS);
    }

}
