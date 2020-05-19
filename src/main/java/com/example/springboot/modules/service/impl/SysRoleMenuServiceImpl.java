package com.example.springboot.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.modules.dao.SysRoleMenuDao;
import com.example.springboot.modules.entity.SysRoleMenu;
import com.example.springboot.modules.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色与权限关系表(SysRoleMenu)表服务实现类
 *
 * @author hrh
 * @since 2020-05-15 14:44:22
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenu> implements SysRoleMenuService {

}