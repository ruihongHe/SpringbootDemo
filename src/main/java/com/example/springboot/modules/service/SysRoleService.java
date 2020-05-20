package com.example.springboot.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.modules.entity.SysRole;

/**
 * 角色表(SysRole)表服务接口
 *
 * @author hrh
 * @since 2020-05-15 14:44:22
 */
public interface SysRoleService extends IService<SysRole> {

    void saveRole(SysRole sysRole);
}