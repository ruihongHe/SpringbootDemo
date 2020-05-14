package com.example.springboot.modules.controller;

import com.example.springboot.modules.entity.SysMenu;
import com.example.springboot.modules.service.SysMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 菜单权限表(SysMenu)表控制层
 *
 * @author hrh
 * @since 2020-05-14 15:51:03
 */
@RestController
@RequestMapping("sysMenu")
public class SysMenuController {
    /**
     * 服务对象
     */
    @Resource
    private SysMenuService sysMenuService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysMenu selectOne(Long id) {
        return this.sysMenuService.queryById(id);
    }

}