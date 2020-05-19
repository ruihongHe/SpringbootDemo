package com.example.springboot.modules.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * 权限表(SysMenu)表实体类
 *
 * @author hrh
 * @since 2020-05-15 14:44:18
 */
@SuppressWarnings("serial")
@Data
public class SysMenu extends Model<SysMenu> {
    //权限ID
    private Long menuId;
    //权限名称
    private String name;
    //权限标识
    private String perms;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.menuId;
    }
    }