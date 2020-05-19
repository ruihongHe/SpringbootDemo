package com.example.springboot.modules.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.modules.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统用户表(SysUser)表数据库访问层
 *
 * @author hrh
 * @since 2020-05-15 14:44:22
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUser> {

    List<String> queryAllPerms(Long userId);
}