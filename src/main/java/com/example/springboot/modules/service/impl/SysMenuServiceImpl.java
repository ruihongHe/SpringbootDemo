package com.example.springboot.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.modules.dao.SysMenuDao;
import com.example.springboot.modules.entity.SysMenu;
import com.example.springboot.modules.entity.SysUser;
import com.example.springboot.modules.service.SysMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限表(SysMenu)表服务实现类
 *
 * @author hrh
 * @since 2020-05-15 14:44:21
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenu> implements SysMenuService {

    @Override
    public List<SysMenu> selectList(Object o) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysMenu::selectAll,o);
        return this.baseMapper.selectList(queryWrapper);
    }
}