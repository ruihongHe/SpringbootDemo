package com.example.springboot.common.util;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author heruihong
 * @Date 2020/5/5 22:16
 * @Version 1.0
 * 项目返回统一结果
 */
@Data
public class Response implements Serializable {
    private Integer code;
    private String message;
    private Object data;

    private Response(){}

    //通用返回结果为枚举
    public static Response setResult(ResultCodeEnum resultCodeEnum){
       Response R=new Response();
        R.setCode(resultCodeEnum.getCode());
        R.setMessage(resultCodeEnum.getMessage());
        return R;
    }
    //通用返回结果为枚举加数据
    public static Response setResult(ResultCodeEnum resultCodeEnum, Object data){
        Response R=new Response();
        R.setCode(resultCodeEnum.getCode());
        R.setMessage(resultCodeEnum.getMessage());
        R.setData(data);
        return R;
    }
    //自定义返回code和message
    public static Response setResult(Integer code, String message){
        Response R=new Response();
        R.setCode(code);
        R.setMessage(message);
        return R;
    }

    //自定义返回code和message加数据
    public static Response setResult(Integer code, String message, Object data){
        Response R=new Response();
        R.setCode(code);
        R.setMessage(message);
        R.setData(data);
        return R;
    }

}
