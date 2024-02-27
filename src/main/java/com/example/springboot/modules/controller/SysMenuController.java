package com.example.springboot.modules.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.annotation.SysLog;
import com.example.springboot.common.validator.ValidatorUtils;
import com.example.springboot.common.validator.group.AddGroup;
import com.example.springboot.common.validator.group.UpdateGroup;
import com.example.springboot.modules.entity.SysMenu;
import com.example.springboot.modules.service.SysMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 权限表(SysMenu)表控制层
 *
 * @author hrh
 * @since 2020-05-15 14:44:21
 */
@RestController
@RequestMapping("sysMenu")
public class SysMenuController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysMenuService sysMenuService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param sysMenu 查询实体
     * @return 所有数据
     */
    @SysLog("菜单列表")
    @PostMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public R selectAll(Page<SysMenu> page, SysMenu sysMenu) {
        return success(this.sysMenuService.page(page, new QueryWrapper<>(sysMenu)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param nemuId 主键
     * @return 单条数据
     */
    @SysLog("菜单详情")
    @PostMapping("/info")
    @RequiresPermissions("sys:menu:info")
    public R selectOne(@RequestParam Long nemuId) {
        return success(this.sysMenuService.getById(nemuId));
    }

    /**
     * 新增数据
     *
     * @param sysMenu 实体对象
     * @return 新增结果
     */
    @SysLog("新增菜单")
    @PostMapping("/add")
    @RequiresPermissions("sys:menu:save")
    public R insert(@RequestBody SysMenu sysMenu) {
        ValidatorUtils.validateEntity(sysMenu, AddGroup.class);
        return success(this.sysMenuService.save(sysMenu));
    }

    /**
     * 修改数据
     *
     * @param sysMenu 实体对象
     * @return 修改结果
     */
    @SysLog("更新菜单")
    @PostMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public R update(@RequestBody SysMenu sysMenu) {
        ValidatorUtils.validateEntity(sysMenu, UpdateGroup.class);
        return success(this.sysMenuService.updateById(sysMenu));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @SysLog("删除菜单")
    @PostMapping("/delete")
    @RequiresPermissions("sys:menu:delete")
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.sysMenuService.removeByMenuIds(idList));
    }
}