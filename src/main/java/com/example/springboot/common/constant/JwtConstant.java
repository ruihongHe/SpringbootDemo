package com.example.springboot.common.constant;

/**
 * @Author hrh
 * @Date 2020/5/29 16:20
 * @Version 1.0
 */
public interface JwtConstant {


    /**
     * JWT-account:
     */
   String USERNAME = "username";
    /**
     * redis-key-前缀-shiro:cache:
     */
   String PREFIX_SHIRO_CACHE = "shiro:cache:";
    /**
     * redis-key-前缀-shiro:refresh_token:
     */
    String PREFIX_SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";

    /**
     * JWT-currentTimeMillis:
     */
    String CURRENT_TIME_MILLIS = "currentTimeMillis";
}
