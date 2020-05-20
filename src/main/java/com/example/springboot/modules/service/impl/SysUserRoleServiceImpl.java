package com.example.springboot.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.modules.dao.SysUserRoleDao;
import com.example.springboot.modules.entity.SysUserRole;
import com.example.springboot.modules.service.SysUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 用户与角色关系表(SysUserRole)表服务实现类
 *
 * @author hrh
 * @since 2020-05-15 14:44:22
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRole> implements SysUserRoleService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        //先删除用户与角色关系
        this.remove(new QueryWrapper<SysUserRole>().eq("user_id", userId));

        if(roleIdList == null || roleIdList.size() == 0){
            return ;
        }
        //保存用户与角色关系
        for(Long roleId : roleIdList){
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleId);
            this.save(sysUserRole);
        }
    }

    @Override
    public List<Long> queryRoleIdList(Long userId) {
        return baseMapper.queryRoleIdList(userId);
    }
}