package com.example.springboot.modules.controller;

import com.example.springboot.common.annotation.NoRepeatSubmit;
import com.example.springboot.modules.entity.SysUser;
import com.example.springboot.modules.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户信息表(SysUser)表控制层
 *
 * @author hrh
 * @since 2020-05-14 15:57:16
 */
@Api(tags = "用户类")
@RestController
@RequestMapping("sysUser")
public class SysUserController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value="获取用户列表", notes="查询所有用户")
    @GetMapping("selectOne")
    @NoRepeatSubmit(time = 3)
    public SysUser selectOne(Long id) {
        return this.sysUserService.queryById(id);
    }

}