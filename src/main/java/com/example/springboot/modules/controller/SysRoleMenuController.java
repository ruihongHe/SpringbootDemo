package com.example.springboot.modules.controller;

import com.example.springboot.modules.entity.SysRoleMenu;
import com.example.springboot.modules.service.SysRoleMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色和菜单关联表(SysRoleMenu)表控制层
 *
 * @author hrh
 * @since 2020-05-14 15:56:51
 */
@RestController
@RequestMapping("sysRoleMenu")
public class SysRoleMenuController {
    /**
     * 服务对象
     */
    @Resource
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysRoleMenu selectOne(Long id) {
        return this.sysRoleMenuService.queryById(id);
    }

}