package com.example.springboot.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.modules.entity.SysRoleMenu;

import java.util.List;

/**
 * 角色与权限关系表(SysRoleMenu)表服务接口
 *
 * @author hrh
 * @since 2020-05-15 14:44:22
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {


    void saveOrUpdate(Long roleId, List<Long> menuIdList);
}