package com.example.springboot.modules.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.example.springboot.common.validator.group.AddGroup;
import com.example.springboot.common.validator.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 权限表(SysMenu)表实体类
 *
 * @author hrh
 * @since 2020-05-15 14:44:18
 */
@SuppressWarnings("serial")
@Data
public class SysMenu extends Model<SysMenu> {
    /**
     * 权限ID
     */
    @TableId(type = IdType.AUTO)
    private Long menuId;
    /**
     * 权限名称
     */
    @NotBlank(message="菜单名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String name;
    /**
     * 权限标识
     */
    private String perms;
    /**
     *创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

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