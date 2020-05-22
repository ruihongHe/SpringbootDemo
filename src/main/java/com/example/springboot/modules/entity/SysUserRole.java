package com.example.springboot.modules.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户与角色关系表(SysUserRole)表实体类
 *
 * @author hrh
 * @since 2020-05-15 14:44:22
 */
@Data
public class SysUserRole extends Model<SysUserRole> {
    //ID
    @TableId(type = IdType.AUTO)
    private Long id;
    //用户ID
    private Long userId;
    //角色ID
    private Long roleId;
}