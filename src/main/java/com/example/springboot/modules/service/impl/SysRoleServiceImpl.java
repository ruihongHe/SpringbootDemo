package com.example.springboot.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.modules.dao.SysRoleDao;
import com.example.springboot.modules.entity.SysRole;
import com.example.springboot.modules.service.SysRoleMenuService;
import com.example.springboot.modules.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 角色表(SysRole)表服务实现类
 *
 * @author hrh
 * @since 2020-05-15 14:44:22
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRole> implements SysRoleService {
    @Resource
    private SysRoleMenuService sysRoleMenuService;


    @Override
    public void saveRole(SysRole sysRole) {
    sysRole.setCreateTime(new Date());
    this.save(sysRole);
    //保存角色关联的菜单
    sysRoleMenuService.saveOrUpdate(sysRole.getRoleId(),sysRole.getMenuIdList());


    }
}