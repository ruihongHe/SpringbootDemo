package com.example.springboot.modules.controller;

import com.example.springboot.modules.entity.SysUserRole;
import com.example.springboot.modules.service.SysUserRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户和角色关联表(SysUserRole)表控制层
 *
 * @author hrh
 * @since 2020-05-14 15:59:19
 */
@RestController
@RequestMapping("sysUserRole")
public class SysUserRoleController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserRoleService sysUserRoleService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysUserRole selectOne(Long id) {
        return this.sysUserRoleService.queryById(id);
    }

}