package com.alex.dragblog.xo.vo;

import com.alex.dragblog.base.validator.annotion.IntegerNotNull;
import com.alex.dragblog.base.validator.annotion.NotBlank;
import com.alex.dragblog.base.validator.group.Update;
import com.alex.dragblog.base.vo.BaseVo;
import com.baomidou.mybatisplus.core.injector.methods.Insert;
import lombok.Data;

/**
 *description:  菜单vo
 *author:       alex
 *createDate:   2020/7/4 15:34
 *version:      1.0.0
 */
@Data
public class CategoryMenuVo extends BaseVo<CategoryMenuVo> {

    /**
     * 菜单名称
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String name;

    /**
     * 菜单级别 （一级分类，二级分类）
     */
    @IntegerNotNull(groups = {Insert.class, Update.class})
    private Integer menuLevel;

    /**
     * 菜单类型 （菜单，按钮）
     */
    @IntegerNotNull(groups = {Insert.class, Update.class})
    private Integer menuType;

    /**
     * 介绍
     */
    private String summary;

    /**
     * Icon图标
     */
    private String icon;

    /**
     * 父UID
     */
    private String parentUid;

    /**
     * URL地址
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String url;

    /**
     * 排序字段(越大越靠前)
     */
    private Integer sort;

    /**
     * 是否显示  1: 是  0: 否
     */
    @IntegerNotNull(groups = {Insert.class, Update.class})
    private Integer isShow;
}
