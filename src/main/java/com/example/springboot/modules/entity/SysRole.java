package com.example.springboot.modules.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.example.springboot.common.validator.group.AddGroup;
import com.example.springboot.common.validator.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色表(SysRole)表实体类
 *
 * @author hrh
 * @since 2020-05-15 14:44:22
 */
@SuppressWarnings("serial")
@Data
public class SysRole extends Model<SysRole> {
    /**
     * 角色ID
     */
    @TableId(type = IdType.AUTO)
    private Long roleId;
    /**
     * 角色名称
     */
    @NotBlank(message="角色名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String roleName;
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
     * 菜单ID列表
     */
    @TableField(exist=false)
    private List<Long> menuIdList;


    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.roleId;
    }
    }