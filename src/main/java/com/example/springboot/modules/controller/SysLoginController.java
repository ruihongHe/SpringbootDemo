package com.example.springboot.modules.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.example.springboot.common.util.Response;
import com.example.springboot.common.util.ResultCodeEnum;
import com.example.springboot.modules.shiro.ShiroUtils;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author hrh
 * @Date 2020/5/18 11:10
 * @Version 1.0
 */
@Controller
public class SysLoginController  {


    @Autowired
    private Producer producer;

    //生成验证码图片
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        HttpSession session = request.getSession();
        // 将生成好的图片放入会话中
       // session.setAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        response.addHeader("token",ShiroUtils.getSession().getId().toString());
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }


    /**
     * 登录
     */
    @ResponseBody
    @PostMapping("/sys/login")
    public Response login(String username, String password/*, String captcha*/) throws Exception {
        Map<String,Object> map = new HashMap<>();
       /* String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
        if(!captcha.equalsIgnoreCase(kaptcha)){
            return Response.setResult(500,"验证码不正确");
        }*/
        try{
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
        }catch (UnknownAccountException e) {
            return Response.setResult(500,e.getMessage());
        }catch (IncorrectCredentialsException e) {
            return Response.setResult(500,"账号或密码不正确");
        }catch (LockedAccountException e) {
            return Response.setResult(500,"账号已被锁定,请联系管理员");
        }catch (AuthenticationException e) {
            return Response.setResult(500,"账户验证失败");
        }
        map.put("token",ShiroUtils.getSession().getId().toString());
        return Response.setResult(ResultCodeEnum.SUCCESS,map);
    }

    /**
     * 退出
     */
    @GetMapping("/sys/logout")
    public void logout() {
        ShiroUtils.logout();
    }


}
