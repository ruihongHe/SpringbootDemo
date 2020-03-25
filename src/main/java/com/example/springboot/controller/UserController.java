package com.example.springboot.controller;


import com.example.springboot.dto.User;
import com.example.springboot.service.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Api(tags = "用户控制类")
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
        log.info("select all user");
        List<User> result=userService.findAll();
        log.info("result：{}", new Gson().toJson(result));
    return result;
    }


    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value = "/addUser",method=RequestMethod.POST)
    public void  addUser(@RequestBody User user){
        log.info("Request parameter：{}",new Gson().toJson(user));
         userService.addUser(user);

    }



    @ApiOperation(value="添加临时用户", notes="创建临时用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value = "/addUserByRedis",method=RequestMethod.POST)
    public void  addUserByRedis(@RequestBody User user){
        log.info("Request parameter：{}", new Gson().toJson(user));
        userService.addUserByRedis(user);

    }

    @ApiOperation(value="查询临时用户", notes="查询临时用户")
    @ApiImplicitParam(name = "account", value = "账号", required = true, dataType = "String")
    @RequestMapping(value = "/getUserByRedis",method=RequestMethod.POST)
    public Object  getUserByRedis(@RequestBody String account){
        log.info("Request parameter：{}",account.toString());
        User user=userService.getUserByRedis(account);
        return user;
    }
}
