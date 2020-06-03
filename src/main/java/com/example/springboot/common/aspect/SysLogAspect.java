package com.example.springboot.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot.common.annotation.SysLog;
import com.example.springboot.common.exception.CmsException;
import com.example.springboot.common.util.HttpContextUtils;
import com.example.springboot.common.util.IPUtils;
import com.example.springboot.modules.entity.SysUser;
import com.example.springboot.modules.shiro.ShiroUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统日志，切面处理类
 *
 * @author Mark sunlightcs@gmail.com
 */
@Aspect
@Component
@Log4j2
public class SysLogAspect {
	
	@Pointcut("@annotation(com.example.springboot.common.annotation.SysLog)")
	public void logPointCut() { 
		
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		//保存日志
		saveSysLog(point, time);
		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Map<String,Object> logsmap=new HashMap<>();
		SysLog syslog = method.getAnnotation(SysLog.class);
		if(syslog != null){
			//注解上的描述
			logsmap.put("Operation",syslog.value());
		}

		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		logsmap.put("Method",className + "." + methodName + "()");


		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = JSONObject.toJSONString(args[0]);
			logsmap.put("Params",params);
		}catch (Exception e){
		   throw  new CmsException(501,"获取请求参数异常！");
		}

		//获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		//设置IP地址
		logsmap.put("Ip", IPUtils.getIpAddr(request));
		//用户名
		String username = ShiroUtils.getUsername();
		logsmap.put("Username",username);
		logsmap.put("Time",time);
		log.info(logsmap.toString());
	}
}
