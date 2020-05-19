package com.example.springboot.common.exception;

import com.example.springboot.common.util.ExceptionUtil;
import com.example.springboot.common.util.Response;
import com.example.springboot.common.util.ResultCodeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

/**
 * @Author heruihong
 * @Date 2020/5/5 23:38
 * @Version 1.0
 */
@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    /**-------- 通用异常处理方法 --------**/
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response error(Exception e) {
        log.error(ExceptionUtil.getMessage(e));
        return Response.setResult(ResultCodeEnum.ERROR);
    }

    /**-------- 指定异常处理方法 --------**/
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Response error(NullPointerException e) {
        log.error(ExceptionUtil.getMessage(e));
        return Response.setResult(ResultCodeEnum.NULL_POINT);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseBody
    public Response error(IndexOutOfBoundsException e) {
        log.error(ExceptionUtil.getMessage(e));
        return Response.setResult(ResultCodeEnum.HTTP_CLIENT_ERROR);
    }

    /**-------- 自定义定异常处理方法 --------**/
    @ExceptionHandler(CmsException.class)
    @ResponseBody
    public Response error(CmsException e) {
        log.error(ExceptionUtil.getMessage(e));
        return Response.setResult(e.getCode(),e.getMessage(),null);
    }
}
