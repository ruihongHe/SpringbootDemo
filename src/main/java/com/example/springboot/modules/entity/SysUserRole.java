package com.example.springboot.modules.entity;

import java.io.Serializable;

/**
 * 用户和角色关联表(SysUserRole)实体类
 *
 * @author hrh
 * @since 2020-05-14 15:59:18
 */
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = 585142353366185680L;
    /**
    * 用户ID
    */
    private Long userId;
    /**
    * 角色ID
    */
    private Long roleId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}