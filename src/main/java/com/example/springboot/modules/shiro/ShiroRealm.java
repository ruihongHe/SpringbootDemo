package com.example.springboot.modules.shiro;

import com.example.springboot.common.constant.JwtConstant;
import com.example.springboot.common.jwt.JwtToken;
import com.example.springboot.common.util.JwtUtil;
import com.example.springboot.modules.entity.SysMenu;
import com.example.springboot.modules.entity.SysUser;
import com.example.springboot.modules.service.SysMenuService;
import com.example.springboot.modules.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;


import javax.annotation.Resource;
import java.util.*;

/**
 * @Author hrh
 * @Date 2020/5/15 14:31
 * @Version 1.0
 */
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysMenuService sysMenuService;


    /**
     * 大坑，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof JwtToken;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = JwtUtil.getClaim(principalCollection.toString(), JwtConstant.USERNAME);
        SysUser user =sysUserService.selectUserByName(username) ;
        List<String> permsList;
        //系统管理员，拥有最高权限
        if(user.getUserId()== 1){
            List<SysMenu> menuList = sysMenuService.selectList();
            permsList = new ArrayList<>(menuList.size());
            for(SysMenu menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = sysUserService.queryAllPerms(user.getUserId());
        }

        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        // 解密获得用户名username，用于和数据库进行对比
        String username = JwtUtil.getClaim(token, JwtConstant.USERNAME);
        // 帐号为空
        if (StringUtils.isBlank(username)) {
            throw new AuthenticationException("Token中帐号为空(The account in Token is empty.)");
        }
      //查询用户
       SysUser user= sysUserService.selectUserByName(username);;
        //判断账号是否存在
        if (user == null) {
            throw new AuthenticationException();
        }
        //判断账号是否被冻结
        if (user.getState()==null||user.getState().equals("PROHIBIT")){
            throw new LockedAccountException();
        }
        return new SimpleAuthenticationInfo(token,token, ByteSource.Util.bytes(user.getSalt()),"ShiroRealm");
    }

}
