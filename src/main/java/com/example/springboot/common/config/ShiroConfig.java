package com.example.springboot.common.config;

import com.example.springboot.common.filter.JwtFilter;
import com.example.springboot.modules.shiro.JWTCredentialsMatcher;
import com.example.springboot.modules.shiro.ShiroRealm;
import com.example.springboot.modules.shiro.ShiroUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author hrh
 * @Date 2020/5/15 11:26
 * @Version 1.0
 */

@Configuration
public class ShiroConfig {


    /**
     * 安全管理器
     */

    @Bean("securityManager")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        // 关闭Shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        securityManager.setRememberMeManager(null);
        return securityManager;
    }

    /**
     * 身份验证器
     */
    @Bean("shiroRealm")
    public ShiroRealm shiroRealm() {
        ShiroRealm shiroRealm=new ShiroRealm();
        shiroRealm.setCredentialsMatcher(new JWTCredentialsMatcher());
        return  shiroRealm;
    }
    /**
     * 设置加密

    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.hashAlgorithmName);
        shaCredentialsMatcher.setHashIterations(ShiroUtils.hashIterations);
     return shaCredentialsMatcher;
    }*/

    /**
     * Shiro基础配置
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // 添加自己的过滤器取名为jwt
        Map<String, Filter> filterMap = new HashMap<>(16);
        filterMap.put("jwt", new JwtFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(securityManager);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>(16);
        filterChainDefinitionMap.put("/sys/login", "anon");
        filterChainDefinitionMap.put("/**","jwt");
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factoryBean;
    }

    @Bean("lifecycleBeanPostProcessor")
    public static  LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
