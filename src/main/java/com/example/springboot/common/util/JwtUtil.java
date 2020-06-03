package com.example.springboot.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springboot.common.constant.JwtConstant;
import com.example.springboot.common.exception.CmsException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @Author hrh
 * @Date 2020/5/29 11:23
 * @Version 1.0
 */
@Log4j2
@Component
public class JwtUtil {


    /**
     * 过期时间改为从配置文件获取
     */
    private static String accessTokenExpireTime;

    /**
     * JWT认证加密私钥(Base64加密)
     */
    private static String encryptJWTKey;

    @Value("${accessTokenExpireTime}")
    public void setAccessTokenExpireTime(String accessTokenExpireTime) {
        JwtUtil.accessTokenExpireTime = accessTokenExpireTime;
    }

    @Value("${encryptJWTKey}")
    public void setEncryptJWTKey(String encryptJWTKey) {
        JwtUtil.encryptJWTKey = encryptJWTKey;
    }


    /**
     *  校验token
     */
    public static boolean checkToken(String token){
        try {
            // 帐号加JWT私钥解密
            String secret = getClaim(token, JwtConstant.USERNAME) + Base64ConvertUtil.decode(encryptJWTKey);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            log.error("JWTToken认证解密出现UnsupportedEncodingException异常:{}", e.getMessage());
            throw new CmsException(500,"JWTToken认证解密出现UnsupportedEncodingException异常:" + e.getMessage());
        }
    }

    /**
     *  获得Token中的信息无需secret解密也能获得
     */
    public static String getClaim(String token, String claim){
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            log.error("解密Token中的公共信息出现JWTDecodeException异常:{}", e.getMessage());
            throw new CmsException(500,"解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
        }
    }


    /**
     *  创建token
     */
    public static String createToken(String username, String currentTimeMillis){
        try {
            // 帐号加JWT私钥加密
            String secret = username + Base64ConvertUtil.decode(encryptJWTKey);
            // 此处过期时间是以毫秒为单位，所以乘以1000
            Date date = new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpireTime) * 1000);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带account帐号信息
            return JWT.create()
                    .withClaim(JwtConstant.USERNAME, username)
                    .withClaim(JwtConstant.CURRENT_TIME_MILLIS, currentTimeMillis)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            log.error("JWTToken加密出现UnsupportedEncodingException异常:{}", e.getMessage());
            throw new CmsException(500,"JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
        }
    }


}
