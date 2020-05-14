package com.example.springboot.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot.common.annotation.NoRepeatSubmit;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;
/**
 *防止重复请求切面处理
 *
 * @author Administrator*/
@Aspect
@Component
@Log4j2
public class NoRepeatSubmitAop {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static final String JWT_TOKEN_KEY = "jwt-token";


    @Around(value = "@annotation(com.example.springboot.common.annotation.NoRepeatSubmit)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String jwtToken = request.getHeader(JWT_TOKEN_KEY);
        String key = DigestUtils.md5DigestAsHex((jwtToken + "-" + request.getRequestURL().toString()+"-"+ JSONObject.toJSONString(request.getParameterMap())).getBytes());
        if (redisTemplate.opsForValue().get(key) == null) {
            try {
                Object o = pjp.proceed();
                MethodSignature signature = (MethodSignature) pjp.getSignature();
                NoRepeatSubmit noRepeatSubmit = signature.getMethod().getAnnotation(NoRepeatSubmit.class);
                // 默认1秒内统一用户同一个地址同一个参数，视为重复提交
                redisTemplate.opsForValue().set(key, "0",noRepeatSubmit.time(), TimeUnit.SECONDS);
                return o;
            }catch (Exception e){
                throw new Exception(e.getMessage(),e.getCause());
            }
        } else {
            throw new Exception("重复提交");
        }
    }

}
