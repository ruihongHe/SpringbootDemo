package com.example.springboot.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.modules.dao.SysRoleMenuDao;
import com.example.springboot.modules.entity.SysRoleMenu;
import com.example.springboot.modules.entity.SysUserRole;
import com.example.springboot.modules.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色与权限关系表(SysRoleMenu)表服务实现类
 *
 * @author hrh
 * @since 2020-05-15 14:44:22
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenu> implements SysRoleMenuService {

    @Override
    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
        //先删除用户与角色关系
        this.remove(new QueryWrapper<SysRoleMenu>().eq("role_id", roleId));

        if(menuIdList == null || menuIdList.size() == 0){
            return ;
        }
        //保存用户与角色关系
        menuIdList.forEach(menuId->{
            SysRoleMenu sysRoleMenu=new SysRoleMenu();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(menuId);
            this.save(sysRoleMenu);
        });
    }

    @Override
    public void removeByroleId(List<Long> idList) {
        if(idList == null || idList.size() == 0){
            return ;
        }

        idList.forEach(roleId->{
            this.remove(new QueryWrapper<SysRoleMenu>().eq("role_id", roleId));
        });

    }

    @Override
    public void removeByMenuId(List<Long> idList) {
        if(idList == null || idList.size() == 0){
            return ;
        }

        idList.forEach(menuId->{
            this.remove(new QueryWrapper<SysRoleMenu>().eq("menu_id", menuId));
        });
    }

    @Override
    public List<Long> querymenuIdList(Long roleId) {

        return baseMapper.querymenuIdList(roleId);
    }

}