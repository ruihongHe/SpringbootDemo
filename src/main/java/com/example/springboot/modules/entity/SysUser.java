package com.example.springboot.modules.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.example.springboot.common.validator.group.AddGroup;
import com.example.springboot.common.validator.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系统用户表(SysUser)表实体类
 *
 * @author hrh
 * @since 2020-05-15 14:44:22
 */
@SuppressWarnings("serial")
@Data
public class SysUser extends Model<SysUser> {

    /**
     *用户ID
     */
    @NotBlank(message="用户id不能为空", groups = {UpdateGroup.class})
    @TableId(type = IdType.AUTO)
    private Long userId;
    /**
     *用户名
     */
    @NotBlank(message="用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String username;
    /**
     *密码
     */
    @NotBlank(message="密码不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    /**
     *盐值
     */
    private String salt;
    /**
     *状态:NORMAL正常  PROHIBIT禁用
     */
    private String state;
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
     * 角色ID列表
     */
    @TableField(exist=false)
    private List<Long> roleIdList;
    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.userId;
    }
    }