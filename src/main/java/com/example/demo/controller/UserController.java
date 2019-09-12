package com.example.demo.controller;


import com.example.demo.dto.User;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {

    private static final Logger log= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;



    @ApiOperation(value="获取用户列表", notes="查询所有用户")
    @RequestMapping(value = "/findAll", method= RequestMethod.POST)
    public Object findAll(){
        log.info("查询所有用户");
      List<User> result=userService.findAll();
        log.info("返回结果：{}",result);
    return result;
    }


    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value = "/addUser",method=RequestMethod.POST)
    public void  addUser(@RequestBody User user){
        log.info("请求参数：{}",user.toString());
         userService.addUser(user);

    }


}
