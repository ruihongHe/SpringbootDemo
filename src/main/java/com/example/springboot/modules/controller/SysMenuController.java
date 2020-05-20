package com.example.springboot.modules.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.validator.ValidatorUtils;
import com.example.springboot.common.validator.group.AddGroup;
import com.example.springboot.common.validator.group.UpdateGroup;
import com.example.springboot.modules.entity.SysMenu;
import com.example.springboot.modules.service.SysMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    @GetMapping
    @RequiresPermissions("sys:menu:list")
    public R selectAll(Page<SysMenu> page, SysMenu sysMenu) {
        return success(this.sysMenuService.page(page, new QueryWrapper<>(sysMenu)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @RequiresPermissions("sys:menu:info")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.sysMenuService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysMenu 实体对象
     * @return 新增结果
     */
    @PostMapping
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
    @PutMapping
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
    @DeleteMapping
    @RequiresPermissions("sys:menu:delete")
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.sysMenuService.removeByIds(idList));
    }
}