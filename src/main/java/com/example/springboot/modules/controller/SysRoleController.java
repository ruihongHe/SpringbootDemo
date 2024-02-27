package com.example.springboot.modules.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.annotation.SysLog;
import com.example.springboot.common.util.Response;
import com.example.springboot.common.util.ResultCodeEnum;
import com.example.springboot.common.validator.ValidatorUtils;
import com.example.springboot.common.validator.group.AddGroup;
import com.example.springboot.modules.entity.SysRole;
import com.example.springboot.modules.service.SysRoleMenuService;
import com.example.springboot.modules.service.SysRoleService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 角色表(SysRole)表控制层
 *
 * @author hrh
 * @since 2020-05-15 14:44:22
 */
@RestController
@RequestMapping("sysRole")
public class SysRoleController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param sysRole 查询实体
     * @return 所有数据
     */
    @SysLog("角色列表")
    @PostMapping("/list")
    @RequiresPermissions("sys:role:list")
    public R selectAll(Page<SysRole> page, SysRole sysRole) {
        return success(this.sysRoleService.page(page, new QueryWrapper<>(sysRole)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param roleId 主键
     * @return 单条数据
     */
    @SysLog("角色详情")
    @PostMapping("/info")
    @RequiresPermissions("sys:role:info")
    public R selectOne(@RequestParam Long roleId) {
        SysRole sysRole=sysRoleService.getById(roleId);
        //查询关联的菜单id
        List<Long> menuIdList=sysRoleMenuService.querymenuIdList(roleId);
        sysRole.setMenuIdList(menuIdList);
        return success(sysRole);
    }

    /**
     * 新增数据
     *
     * @param sysRole 实体对象
     * @return 新增结果
     */
    @SysLog("新增角色")
    @PostMapping("/add")
    @RequiresPermissions("sys:role:save")
    public Response insert(@RequestBody SysRole sysRole) {
        ValidatorUtils.validateEntity(sysRole, AddGroup.class);
        sysRoleService.saveRole(sysRole);
        return Response.setResult(ResultCodeEnum.SUCCESS);
    }

    /**
     * 修改数据
     *
     * @param sysRole 实体对象
     * @return 修改结果
     */
    @SysLog("更新角色")
    @PostMapping("/update")
    @RequiresPermissions("sys:role:update")
    public R update(@RequestBody SysRole sysRole) {
        return success(this.sysRoleService.updateByRoleId(sysRole));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @SysLog("删除角色")
    @PostMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.sysRoleService.removeByRoleIds(idList));
    }
}