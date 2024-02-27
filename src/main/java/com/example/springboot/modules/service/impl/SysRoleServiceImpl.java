package com.example.springboot.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.modules.dao.SysRoleDao;
import com.example.springboot.modules.entity.SysRole;
import com.example.springboot.modules.service.SysRoleMenuService;
import com.example.springboot.modules.service.SysRoleService;
import com.example.springboot.modules.service.SysUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.Date;
import java.util.List;

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

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(SysRole sysRole) {
    sysRole.setCreateTime(new Date());
    this.save(sysRole);
    //保存角色关联的菜单
    sysRoleMenuService.saveOrUpdate(sysRole.getRoleId(),sysRole.getMenuIdList());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByRoleId(SysRole sysRole){
        sysRole.setUpdateTime(new Date());
        //更新角色关联的菜单
        sysRoleMenuService.saveOrUpdate(sysRole.getRoleId(),sysRole.getMenuIdList());
        return this.updateById(sysRole);
    }

    @Override
    public Object removeByRoleIds(List<Long> idList) {
        //删除角色关联的菜单
        sysRoleMenuService.removeByroleId(idList);
        //删除角色关联的用户
        sysUserRoleService.removeByroleId(idList);
        return this.removeByIds(idList);
    }
}