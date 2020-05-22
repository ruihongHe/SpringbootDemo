package com.example.springboot.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.modules.entity.SysMenu;

import java.util.List;

/**
 * 权限表(SysMenu)表服务接口
 *
 * @author hrh
 * @since 2020-05-15 14:44:20
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> selectList();

    Object removeByMenuIds(List<Long> idList);
}