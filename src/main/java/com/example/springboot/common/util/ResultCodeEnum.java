package com.example.springboot.common.util;


import lombok.Getter;

/**
 * @Author heruihong
 * @Date 2020/5/5 22:02
 * @Version 1.0
 */

@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    UNKNOWN_ERROR(201,"未知错误"),
    ERROR(202,"错误"),
    NULL_POINT(203,"空指针异常"),
    HTTP_CLIENT_ERROR(204,"httpClient异常")
    ;




    //返回状态码
    private Integer code;

    //返回信息
    private String message;

    ResultCodeEnum(Integer code,String message){
        this.code=code;
        this.message=message;
    }


}
