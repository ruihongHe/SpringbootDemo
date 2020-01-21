package com.example.demo.controller;


import com.example.demo.dto.User;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Log4j2
@RestController
@RequestMapping("/User")
public class UserController {

    //private static final Logger log= LoggerFactory.getLogger(UserController.class);

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



    @ApiOperation(value="添加临时用户", notes="创建临时用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value = "/addUserByRedis",method=RequestMethod.POST)
    public void  addUserByRedis(@RequestBody User user){
        log.info("请求参数：{}",user.toString());
        userService.addUserByRedis(user);

    }

    @ApiOperation(value="查询临时用户", notes="查询临时用户")
    @ApiImplicitParam(name = "account", value = "账号", required = true, dataType = "String")
    @RequestMapping(value = "/getUserByRedis",method=RequestMethod.POST)
    public Object  getUserByRedis(@RequestBody String account){
        log.info("请求参数：{}",account.toString());
        User user=userService.getUserByRedis(account);
        return user;
    }
}
