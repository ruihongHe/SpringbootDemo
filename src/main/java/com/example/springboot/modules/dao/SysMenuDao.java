package com.example.springboot.modules.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.modules.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限表(SysMenu)表数据库访问层
 *
 * @author hrh
 * @since 2020-05-15 14:44:19
 */
@Mapper
public interface SysMenuDao extends BaseMapper<SysMenu> {

}