package com.example.springboot.modules.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统用户表(SysUser)表实体类
 *
 * @author hrh
 * @since 2020-05-15 14:44:22
 */
@SuppressWarnings("serial")
@Data
public class SysUser extends Model<SysUser> {
    //用户ID
    private Long userId;
    //用户名
    private String username;
    //密码
    private String password;
    //盐值
    private String salt;
    //状态:NORMAL正常  PROHIBIT禁用
    private String state;

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