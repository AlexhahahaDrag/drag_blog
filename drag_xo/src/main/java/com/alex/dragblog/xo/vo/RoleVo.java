package com.alex.dragblog.xo.vo;


import com.alex.dragblog.base.validator.annotion.NotBlank;
import com.alex.dragblog.base.validator.group.Update;
import com.baomidou.mybatisplus.core.injector.methods.Insert;

/**
 *description:  角色视图
 *author:       alex
 *createDate:   2020/7/2 22:45
 *version:      1.0.0
 */
public class RoleVo {

    //角色名
    @NotBlank(groups = {Insert.class, Update.class})
    private String roleName;

    //介绍
    private String summary;

    //角色所管辖区域
    private String categoryMenuIds;
}
