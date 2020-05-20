package com.example.springboot.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.modules.entity.SysUserRole;

import java.io.Serializable;
import java.util.List;

/**
 * 用户与角色关系表(SysUserRole)表服务接口
 *
 * @author hrh
 * @since 2020-05-15 14:44:22
 */
public interface SysUserRoleService extends IService<SysUserRole> {


     void saveOrUpdate(Long userId, List<Long> roleIdList);


    List<Long> queryRoleIdList(Long userId);
}