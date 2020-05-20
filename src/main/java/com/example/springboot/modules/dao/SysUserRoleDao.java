package com.example.springboot.modules.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.modules.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户与角色关系表(SysUserRole)表数据库访问层
 *
 * @author hrh
 * @since 2020-05-15 14:44:22
 */
@Mapper
public interface SysUserRoleDao extends BaseMapper<SysUserRole> {

    List<Long> queryRoleIdList(Long userId);
}