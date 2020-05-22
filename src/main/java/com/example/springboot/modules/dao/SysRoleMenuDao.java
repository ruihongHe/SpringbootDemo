package com.example.springboot.modules.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.modules.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色与权限关系表(SysRoleMenu)表数据库访问层
 *
 * @author hrh
 * @since 2020-05-15 14:44:22
 */
@Mapper
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenu> {

    List<Long> querymenuIdList(Long roleId);
}