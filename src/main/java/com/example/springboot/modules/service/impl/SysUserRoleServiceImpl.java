package com.example.springboot.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.modules.dao.SysUserRoleDao;
import com.example.springboot.modules.entity.SysUserRole;
import com.example.springboot.modules.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户与角色关系表(SysUserRole)表服务实现类
 *
 * @author hrh
 * @since 2020-05-15 14:44:22
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRole> implements SysUserRoleService {

}