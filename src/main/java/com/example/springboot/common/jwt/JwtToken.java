package com.example.springboot.common.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author hrh
 * @Date 2020/5/29 11:23
 * @Version 1.0
 */
public class JwtToken implements AuthenticationToken {
    /**
     * Token
     */
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
