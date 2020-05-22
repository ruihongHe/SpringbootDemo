package com.example.springboot.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.modules.entity.SysRole;

import java.util.List;

/**
 * 角色表(SysRole)表服务接口
 *
 * @author hrh
 * @since 2020-05-15 14:44:22
 */
public interface SysRoleService extends IService<SysRole> {

    void saveRole(SysRole sysRole);

    boolean updateByRoleId(SysRole sysRole);

    Object removeByRoleIds(List<Long> idList);
}