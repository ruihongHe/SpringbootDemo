package com.example.springboot.modules.entity;

import java.io.Serializable;

/**
 * 角色和菜单关联表(SysRoleMenu)实体类
 *
 * @author hrh
 * @since 2020-05-14 15:56:51
 */
public class SysRoleMenu implements Serializable {
    private static final long serialVersionUID = 342470809399647101L;
    /**
    * 角色ID
    */
    private Long roleId;
    /**
    * 菜单ID
    */
    private Long menuId;


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

}