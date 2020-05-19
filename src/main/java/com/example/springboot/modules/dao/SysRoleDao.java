package com.example.springboot.modules.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.modules.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色表(SysRole)表数据库访问层
 *
 * @author hrh
 * @since 2020-05-15 14:44:22
 */
@Mapper
public interface SysRoleDao extends BaseMapper<SysRole> {

}