package com.alex.dragblog.commons.entity;

import com.alex.dragblog.base.entity.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 *description:  管理员表
 *author:       alex
 *createDate:   2020/6/26 22:07
 *version:      1.0.0
 */
@Data
@TableName("t_admin")
public class Admin extends SuperEntity<Admin> {

    //用户名
    private String username;

    //角色id
    private String roleUid;

    //密码
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别(1:男2:女)
     */
    private String gender;

    /**
     * 个人头像
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 出生年月日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;

    /**
     * 手机
     */
    private String mobile;

    /**
     * QQ号
     */
    private String qqNumber;

    /**
     * 微信号
     */
    private String weChat;

    /**
     * 职业
     */
    private String occupation;

    /**
     * 自我简介最多150字
     */
    private String summary;

    /**
     * 个人履历（Markdown）
     */
    private String personResume;

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 最后登录时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * github地址
     */
    private String github;

    /**
     * gitee地址
     */
    private String gitee;

    // 以下字段不存入数据库

    /**
     * 用户头像
     */
    @TableField(exist = false)
    private List<String> photoList;

    /**
     * 所拥有的角色名
     */
    @TableField(exist = false)
    private List<String> roleNames;

    /**
     * 所拥有的角色名
     */
    @TableField(exist = false)
    private Role role;

    /**
     * 验证码
     */
    @TableField(exist = false)
    private String validCode;

}
