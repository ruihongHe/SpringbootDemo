package com.example.springboot.modules.shiro;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.springboot.common.constant.JwtConstant;
import com.example.springboot.common.util.JedisUtil;
import com.example.springboot.common.util.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;


@Log4j2
public class JWTCredentialsMatcher implements CredentialsMatcher {
	


    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        String token = (String) authenticationToken.getCredentials();
        String username = JwtUtil.getClaim(token, JwtConstant.USERNAME);
        try {
            // 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
            if (JwtUtil.checkToken(token) && JedisUtil.exists(JwtConstant.PREFIX_SHIRO_REFRESH_TOKEN + username)) {
                // 获取RefreshToken的时间戳
                String currentTimeMillisRedis = JedisUtil.getObject(JwtConstant.PREFIX_SHIRO_REFRESH_TOKEN + username).toString();
                // 获取AccessToken时间戳，与RefreshToken的时间戳对比
                if (JwtUtil.getClaim(token, JwtConstant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
                    return true;
                }
            }
            throw new AuthenticationException("Token已过期(Token expired or incorrect.)");
        } catch (JWTVerificationException e) {
            log.error("Token Error:{}", e.getMessage());

        }
        return false;
    }

}
