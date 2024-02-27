package com.example.springboot.modules.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.annotation.SysLog;
import com.example.springboot.common.constant.JwtConstant;
import com.example.springboot.common.exception.CmsException;
import com.example.springboot.common.util.JedisUtil;
import com.example.springboot.common.util.Response;
import com.example.springboot.common.util.ResultCodeEnum;
import com.example.springboot.common.validator.Assert;
import com.example.springboot.common.validator.ValidatorUtils;
import com.example.springboot.common.validator.group.AddGroup;
import com.example.springboot.common.validator.group.UpdateGroup;
import com.example.springboot.modules.entity.SysUser;
import com.example.springboot.modules.service.SysUserRoleService;
import com.example.springboot.modules.service.SysUserService;
import com.example.springboot.modules.shiro.ShiroUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户表(SysUser)表控制层
 *
 * @author hrh
 * @since 2020-05-15 14:44:22
 */
@RestController
@RequestMapping("sysUser")
public class SysUserController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysUserRoleService sysUserRoleService;


    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param sysUser 查询实体
     * @return 所有数据
     */
    @SysLog("用户列表")
    @PostMapping("/list")
    @RequiresPermissions("sys:user:list")
    public R selectAll(Page<SysUser> page, SysUser sysUser) {
        return success(this.sysUserService.page(page, new QueryWrapper<>(sysUser)));
    }

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    public Response info(){
        Map<String,Object> data=new HashMap<>();
        data.put("user",new ShiroUtils(sysUserService).getUserEntity());
        return Response.setResult(ResultCodeEnum.SUCCESS,data);
    }
    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @PostMapping("/password")
    public Response password(String password, String newPassword){
        Assert.isBlank(newPassword, "新密码不为能空");
        SysUser user= new ShiroUtils(sysUserService).getUserEntity();
        //原密码
        password = ShiroUtils.sha256(password, user.getSalt());
        //新密码
        newPassword = ShiroUtils.sha256(newPassword, user.getSalt());

        //更新密码
        boolean flag = sysUserService.updatePassword(user.getUserId(), password, newPassword);
        if(!flag){
            return Response.setResult(204,"原密码不正确");
        }

        return Response.setResult(ResultCodeEnum.SUCCESS);
    }


    /**
     * 通过主键查询单条数据
     *
     * @param userId 主键
     * @return 单条数据
     */
    @SysLog("查询用户详情")
    @PostMapping("/info")
    @RequiresPermissions("sys:user:info")
    public R selectOne(@RequestParam Long userId) {
        SysUser user=sysUserService.getById(userId);
        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);
        return success(user);
    }

    /**
     * 新增数据
     *
     * @param sysUser 实体对象
     * @return 新增结果
     */
    @SysLog("添加用户")
    @PostMapping("/add")
    @RequiresPermissions("sys:user:save")
    public Response insert(@RequestBody SysUser sysUser) {
        ValidatorUtils.validateEntity(sysUser, AddGroup.class);
        sysUserService.saveUser(sysUser);
        return Response.setResult(ResultCodeEnum.SUCCESS);
    }

    /**
     * 修改数据
     *
     * @param sysUser 实体对象
     * @return 修改结果
     */
    @SysLog("更新用户")
    @PostMapping("/update")
    @RequiresPermissions("sys:user:update")
    public Response update(@RequestBody SysUser sysUser) {
        ValidatorUtils.validateEntity(sysUser, UpdateGroup.class);
        sysUserService.updateUser(sysUser);
        return Response.setResult(ResultCodeEnum.SUCCESS);
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @SysLog("删除用户")
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public Response delete(@RequestBody Long[] idList) {
        if(ArrayUtils.contains(idList, 1L)){
            return Response.setResult(401,"系统管理员不能删除");
        }

        if(ArrayUtils.contains(idList, new ShiroUtils(sysUserService).getUserId())){
            return  Response.setResult(401,"当前用户不能删除");
        }
        sysUserService.removeByUserIds(Arrays.asList(idList));

        return Response.setResult(ResultCodeEnum.SUCCESS);
    }


    /**
     * 退出
     */
    @GetMapping("/logout")
    public Response logout() {
        try{
            if(JedisUtil.exists(JwtConstant.PREFIX_SHIRO_REFRESH_TOKEN+ ShiroUtils.getUsername())){
                if(JedisUtil.delKey(JwtConstant.PREFIX_SHIRO_REFRESH_TOKEN+ ShiroUtils.getUsername())>0){
                    ShiroUtils.logout();
                }
            }}catch (Exception e){
            throw new CmsException(500,"剔除失败，username不存在(Deletion Failed. Account does not exist.)");
        }
        return Response.setResult(ResultCodeEnum.SUCCESS);
    }
}