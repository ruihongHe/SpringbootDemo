package com.example.springboot.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.modules.dao.SysMenuDao;
import com.example.springboot.modules.entity.SysMenu;
import com.example.springboot.modules.entity.SysUser;
import com.example.springboot.modules.service.SysMenuService;
import com.example.springboot.modules.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限表(SysMenu)表服务实现类
 *
 * @author hrh
 * @since 2020-05-15 14:44:21
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenu> implements SysMenuService {
    @Resource
    private SysMenuDao sysMenuDao;
    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @Override
    public List<SysMenu> selectList() {
        return sysMenuDao.selectALL();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object removeByMenuIds(List<Long> idList) {
        //删除角色与菜单的关系
        sysRoleMenuService.removeByMenuId(idList);
        return this.removeByIds(idList);
    }
}