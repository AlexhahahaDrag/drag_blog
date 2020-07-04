package com.alex.dragblog.xo.service;

import com.alex.dragblog.base.service.SuperService;
import com.alex.dragblog.commons.entity.CategoryMenu;
import com.alex.dragblog.xo.vo.CategoryMenuVo;

import java.util.List;
import java.util.Map;

/**
 *description:  菜单服务类
 *author:       alex
 *createDate:   2020/7/4 15:28
 *version:      1.0.0
 */
public interface CategoryMenuService extends SuperService<CategoryMenu> {

    /**
     * description :获取菜单列表
     * author :     alex
     * @param :     categoryMenuVo
     * @return :
     */
    Map<String, Object> getPageList(CategoryMenuVo categoryMenuVo);

    /**
     * description :获取全部菜单列表
     * author :     alex
     * @param :     keyword
     * @return :
     */
    List<CategoryMenu> getAllList(String keyword);

    /**
     * description :获取所有二级菜单-按钮列表
     * author :     alex
     * @param :     keyword
     * @return :
     */
    List<CategoryMenu> getButtonAllList(String keyword);

    /**
     * description :新增菜单
     * author :     alex
     * @param :
     * @return :
     */
    String addCategoryMenu(CategoryMenuVo categoryMenuVo);

    /**
     * description :编辑菜单
     * author :     alex
     * @param :
     * @return :
     */
    String editCategoryMenu(CategoryMenuVo categoryMenuVo);

    /**
     * description :批量删除菜单
     * author :     alex
     * @param :
     * @return :
     */
    String deleteCategoryMenu(CategoryMenuVo categoryMenuVo);

    /**
     * description :置顶菜单
     * author :     alex
     * @param :
     * @return :
     */
    String stickCategoryMenu(CategoryMenuVo categoryMenuVo);
}
