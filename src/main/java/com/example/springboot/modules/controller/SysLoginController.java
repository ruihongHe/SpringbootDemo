package com.example.springboot.modules.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.example.springboot.common.constant.JwtConstant;
import com.example.springboot.common.exception.CmsException;
import com.example.springboot.common.util.JedisUtil;
import com.example.springboot.common.util.JwtUtil;
import com.example.springboot.common.util.Response;
import com.example.springboot.common.util.ResultCodeEnum;
import com.example.springboot.modules.entity.SysUser;
import com.example.springboot.modules.service.SysUserService;
import com.example.springboot.modules.shiro.ShiroUtils;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.imageio.ImageIO;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    @Autowired
    private Producer producer;
    @Resource
    private SysUserService sysUserService;

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
      /*  ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        response.addHeader("token",ShiroUtils.getSession().getId().toString());*/
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
        SysUser user=sysUserService.selectUserByName(username);
        if (user == null) {
            throw new CmsException(500,"该帐号不存在(The account does not exist.)");
        }
        //密码和盐值加密
        String result=ShiroUtils.sha256(password,user.getSalt());
        if(user.getPassword().equals(result)){
            // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            JedisUtil.setObject(JwtConstant.PREFIX_SHIRO_REFRESH_TOKEN + user.getUsername(), currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
            // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
            String token = JwtUtil.createToken(user.getUsername(), currentTimeMillis);
            map.put("token",token);
            return Response.setResult(ResultCodeEnum.SUCCESS,map);
        }else {
            throw new CmsException(500,"帐号或密码错误(Account or Password Error.)");
        }
    }

}
