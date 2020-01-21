package com.example.springboot.controller;

import com.example.springboot.service.impl.ScoketClient;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/websocket")
public class IndexController {

    @Autowired
    private ScoketClient webScoketClient;

    @ApiOperation(value="Socket客户端", notes="发送消息")
    @GetMapping("/sendMessage")
    public String sendMessage(String message){
        webScoketClient.groupSending(message);
        return message;
    }

}
