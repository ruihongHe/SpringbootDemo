package com.example.springboot.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.modules.entity.SysUser;

import java.util.List;

/**
 * 系统用户表(SysUser)表服务接口
 *
 * @author hrh
 * @since 2020-05-15 14:44:22
 */
public interface SysUserService extends IService<SysUser> {

    SysUser selectUserByName(String username);

    List<String> queryAllPerms(Long userId);

    void saveUser(SysUser sysUser);

    void updateUser(SysUser sysUser);

    boolean updatePassword(Long userId, String password, String newPassword);

    void removeByUserIds(List<Long> asList);
}