package com.example.springboot.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.modules.dao.SysUserDao;
import com.example.springboot.modules.entity.SysUser;
import com.example.springboot.modules.service.SysUserRoleService;
import com.example.springboot.modules.service.SysUserService;
import com.example.springboot.modules.shiro.ShiroUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 系统用户表(SysUser)表服务实现类
 *
 * @author hrh
 * @since 2020-05-15 14:44:22
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private SysUserRoleService sysUserRoleService;

    @Override
    public SysUser selectUserByName(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUser::getUsername,username);
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public List<String> queryAllPerms(Long userId) {

        return sysUserDao.queryAllPerms(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(SysUser sysUser) {
        sysUser.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        sysUser.setSalt(salt);
        sysUser.setState("NORMAL");
        sysUser.setPassword(ShiroUtils.sha256(sysUser.getPassword(), sysUser.getSalt()));
        this.save(sysUser);
        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(sysUser.getUserId(), sysUser.getRoleIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(SysUser sysUser) {
        if(StringUtils.isBlank(sysUser.getPassword())){
            sysUser.setPassword(null);
        }else{
            SysUser userEntity = this.getById(sysUser.getUserId());
            sysUser.setPassword(ShiroUtils.sha256(sysUser.getPassword(), userEntity.getSalt()));
        }
        this.updateById(sysUser);
        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(sysUser.getUserId(), sysUser.getRoleIdList());
    }

    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        SysUser user = new SysUser();
        user.setPassword(newPassword);
        return this.update(user,
                new QueryWrapper<SysUser>().eq("user_id", userId).eq("password", password));
    }

}