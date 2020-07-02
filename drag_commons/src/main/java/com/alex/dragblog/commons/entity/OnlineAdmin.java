package com.alex.dragblog.commons.entity;

import lombok.Data;

/**
 *description:  当前在线管理员
 *author:       alex
 *createDate:   2020/7/2 21:58
 *version:      1.0.0
 */
@Data
public class OnlineAdmin {

    //tokenId
    private String tokenId;

    //用户名
    private String username;

    //ip地址
    private String ipAddr;

    //登陆地址
    private String loginLocation;

    //浏览器
    private String browser;

    //操作系统
    private String os;

    //角色
    private String roleName;

    //登陆时间
    private String loginTime;
}
